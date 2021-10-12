package com.example.youtube.ui.authentication

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.youtube.service.base.BaseViewModel
import com.example.youtube.service.repositories.UserRepository
import com.example.youtube.service.models.LiveDataInfo
import com.example.youtube.service.models.LoginResponseInfo


class LoginViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() {
        if (loginDataValidate()) {
            status.value = LiveDataInfo.LOADING
            email.value?.let {
                password.value?.let { it1 ->
                    userRepository.login(it, it1) { isSuccess, response ->
                        if (isSuccess) {
                            when (response) {
                                LoginResponseInfo.LOGGED_IN -> {
                                    status.value = LiveDataInfo.LOADED_SUCCESSFUL
                                    message.value = "Successfully logged in"
                                }
                                LoginResponseInfo.INCORRECT_DATA -> {
                                    status.value = LiveDataInfo.LOADED_EMPTY
                                    message.value = "Incorrect user data"
                                }
                                LoginResponseInfo.UNSPECIFIED_RESPONSE -> {
                                    status.value = LiveDataInfo.ERROR
                                    message.value = "Unknown Error: Unspecified response"
                                }
                                LoginResponseInfo.SERVER_ERROR -> {
                                    status.value = LiveDataInfo.ERROR
                                    message.value = "Server Error: Server side errors"
                                }
                                LoginResponseInfo.UNKNOWN_ERROR -> {
                                    status.value = LiveDataInfo.ERROR
                                    message.value = "Unknown Error: Undefined Error"
                                }
                            }
                        } else {
                            status.value = LiveDataInfo.ERROR
                            message.value = "Server not correct or problems with connection"
                        }
                    }
                }
            }
        }
        else {
            status.value = LiveDataInfo.LOADED_EMPTY
            message.value = "Not valid email address or password"
        }
    }

    fun signup() {
        // TODO Sign Up Activity
    }

    private fun loginDataValidate(): Boolean {
        return if (TextUtils.isEmpty(email.value.toString()) || TextUtils.isEmpty(password.value.toString())) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(email.value.toString()).matches()
        }
    }
}