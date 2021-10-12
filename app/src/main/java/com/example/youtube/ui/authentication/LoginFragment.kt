package com.example.youtube.ui.authentication

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.youtube.MainActivity
import com.example.youtube.R
import com.example.youtube.databinding.LoginBinding
import com.example.youtube.service.models.LiveDataInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: LoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginBinding.inflate(inflater, container, false).apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            binding.viewModel?.email?.value = binding.email.text.toString()
            binding.viewModel?.password?.value = binding.password.text.toString()
            binding.viewModel?.login()
        }

        binding.signupButton.setOnClickListener {
            binding.viewModel?.signup() // TODO Sign up method or action
        }

        setupObservers()
    }

    private fun setupObservers() {
        // Status Observer
        binding.viewModel?.status?.observe(viewLifecycleOwner, Observer {
            when (it) {
                LiveDataInfo.LOADING -> {
                    // Important For Loading animation!
                    binding.loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.animated_loading_gray, 0)
                    (binding.loginButton.compoundDrawables[2] as AnimatedVectorDrawable).start()
                }
                LiveDataInfo.LOADED_SUCCESSFUL -> {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
                else -> {
                    binding.loginButton.setCompoundDrawables(null, null, null, null)
                }
            }
        })

        // Message Observer
        binding.viewModel?.message?.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }
}