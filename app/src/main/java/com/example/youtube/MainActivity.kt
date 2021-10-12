package com.example.youtube

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.youtube.databinding.ActivityMainBinding
import com.example.youtube.service.models.LiveDataInfo
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val presenter: MainPresenter by inject()
    private lateinit var binding: ActivityMainBinding
    private val context = this


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding

        setSupportActionBar(binding.toolbar)

        presenter.firstCheck()

        presenter.status.observe(this, Observer {
            when (it) {
                LiveDataInfo.LOADING -> {
                    binding.toolbar.visibility = View.GONE
                    binding.errorContainer.visibility = View.GONE
                    binding.mainNavHostFragment.visibility = View.GONE
                    binding.iconContainer.visibility = View.VISIBLE
                }
                LiveDataInfo.LOADED_SUCCESSFUL -> {
                    binding.toolbar.visibility = View.VISIBLE
                    binding.mainNavHostFragment.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                    binding.iconContainer.visibility = View.GONE

                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
                    navHostFragment.navController.setGraph(R.navigation.graph_main)
                    navHostFragment.navController.navigateUp()
                }
                LiveDataInfo.LOADED_EMPTY -> {
                    // User checking completed with 4xx error
                    val intent = Intent(context, AuthenticateActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> {
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.GONE
                    binding.mainNavHostFragment.visibility = View.GONE
                    binding.iconContainer.visibility = View.GONE
                }
            }
        })
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