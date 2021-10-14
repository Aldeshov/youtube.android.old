package com.example.youtube.ui.authentication

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.youtube.R
import com.example.youtube.databinding.FragmentLoginBinding
import com.example.youtube.service.models.LiveDataStatus
import com.example.youtube.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false).apply {
            viewModel = loginViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {
            loginViewModel.email.value = binding.email.text.toString()
            loginViewModel.password.value = binding.password.text.toString()
            loginViewModel.login()
        }

        binding.signupButton.setOnClickListener {
            loginViewModel.signup() // TODO Sign up method or action
        }

        setupObservers()
    }

    private fun setupObservers() {
        // Status Observer
        loginViewModel.status.observe(viewLifecycleOwner, {
            when (it) {
                LiveDataStatus.LOADING -> {
                    // Important For Loading animation!
                    binding.loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.animated_loading_gray, 0)
                    (binding.loginButton.compoundDrawables[2] as AnimatedVectorDrawable).start()
                }
                LiveDataStatus.LOADED_SUCCESSFUL -> {
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
        loginViewModel.message.observe(viewLifecycleOwner, {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
    }
}