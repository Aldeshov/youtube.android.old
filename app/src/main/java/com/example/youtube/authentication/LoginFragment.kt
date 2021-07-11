package com.example.youtube.authentication

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.youtube.LoadActivity
import com.example.youtube.R
import com.example.youtube.api.APIService
import com.example.youtube.api.StartService.Companion.coreService
import com.example.youtube.api.StartService.Companion.userDao
import com.example.youtube.database.models.User
import com.example.youtube.models.authentication.LoginResponse
import kotlinx.android.synthetic.main.login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login_button.setOnClickListener {
            error_text.visibility = View.INVISIBLE

            val email = email.text.toString()
            val password = password.text.toString()

            if (loginDataValidate(email, password)) {
                login_button.isEnabled = false

                login_button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.animated_loading_gray, 0)
                val animation = login_button.compoundDrawables[2] as AnimatedVectorDrawable
                animation.start()

                (coreService as APIService).login(email, password)
                    .enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        login_button.isEnabled = true
                        login_button.setCompoundDrawables(null, null, null, null)

                        if (response.code() == 400) {
                            error_text.visibility = View.VISIBLE
                            return
                        }

                        if (response.code() == 200) {
                            val user = User()
                            user.id = 1
                            user.token = response.body()!!.token
                            userDao?.insert(user)
                            val intent = Intent(activity, LoadActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                            return
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        login_button.isEnabled = true
                        login_button.setCompoundDrawables(null, null, null, null)
                        Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    private fun loginDataValidate(email: String, password: String): Boolean {
        if (email == "" || password.length < 4) {
            return false
        }
        return true
    }
}