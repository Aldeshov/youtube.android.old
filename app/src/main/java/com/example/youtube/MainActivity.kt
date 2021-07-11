package com.example.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.youtube.api.APIService
import com.example.youtube.api.StartService.Companion.coreService
import com.example.youtube.models.VideoContent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.graph_main)
        navHostFragment.navController.navigateUp()

        val context = this

        (coreService as APIService).getVideoContents()
            .enqueue(object : Callback<ArrayList<VideoContent>> {
            override fun onResponse(call: Call<ArrayList<VideoContent>>,
                                    response: Response<ArrayList<VideoContent>>) {
                val items = ArrayList<VideoContent>()
                items.addAll(response.body()!!)

                val bundle = Bundle()
                bundle.putParcelableArrayList("video_contents", items)  // Key, value

                navHostFragment.navController.navigate(R.id.action_loadingFragment_to_listFragment, bundle)
            }

            override fun onFailure(call: Call<ArrayList<VideoContent>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
                navHostFragment.navController.navigate(R.id.action_loadingFragment_to_errorFragment)
            }
        })
    }
}