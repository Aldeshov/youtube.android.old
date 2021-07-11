package com.example.youtube

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.youtube.api.APIService
import com.example.youtube.api.StartService
import com.example.youtube.api.StartService.Companion.coreService
import com.example.youtube.api.StartService.Companion.userDao
import com.example.youtube.models.authentication.User
import com.example.youtube.video_content_list.ErrorFragment
import com.example.youtube.video_content_list.LoadingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)

        StartService() // Start API & Database Services

        val loadingFragment = LoadingFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.load, loadingFragment)
            .commit()

        val context = this

        (coreService as APIService).getUser().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.code() == 401 || response.code() == 400) {
                    userDao?.getUser()?.let { userDao!!.delete(userDao!!.getUser()) }
                    val intent = Intent(context, AuthenticateActivity::class.java)
                    startActivity(intent)
                    finish()
                    return
                }

                if (response.code() == 200) {
                    val user = userDao?.getUser()
                    if (user != null) {
                        user.email = response.body()!!.email
                        user.full_name = response.body()!!.full_name
                        user.avatar = response.body()!!.avatar
                    }
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    return
                }

                val errorFragment = ErrorFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.load, errorFragment)
                    .commit()
                return
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                val errorFragment = ErrorFragment()
                context.supportFragmentManager.beginTransaction()
                    .replace(R.id.load, errorFragment)
                    .commit()
            }
        })
    }
}