package com.example.youtube.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.example.youtube.R

class AuthenticateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authenticate)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.auth_nav_host_fragment) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.graph_authenticate)
        navHostFragment.navController.navigateUp()
    }
}