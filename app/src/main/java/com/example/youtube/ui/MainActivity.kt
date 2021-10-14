package com.example.youtube.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.youtube.R
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.service.models.LiveDataStatus
import com.example.youtube.ui.authentication.AuthenticateActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setSupportActionBar(binding.toolbar)

        mainViewModel.firstCheck()

        setupObservers()
    }

    private fun setupObservers() {
        mainViewModel.status.observe(this, {
            when (it) {
                LiveDataStatus.LOADED_SUCCESSFUL -> {
                    startFragmentManager()
                }
                LiveDataStatus.LOADED_EMPTY -> {
                    switchToAuthenticate()
                }
                else -> {
                    Log.w("Main Activity", "Loading Or Not loaded")
                }
            }
        })

        mainViewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    private fun startFragmentManager() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navHostFragment.navController.setGraph(R.navigation.graph_main)
        navHostFragment.navController.navigateUp()
    }

    private fun switchToAuthenticate() {
        val intent = Intent(this, AuthenticateActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp() = findNavController(R.id.main_nav_host_fragment).navigateUp()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO catch User Icon button click
        return true
    }
}