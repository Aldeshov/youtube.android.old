package com.example.youtube.ui.authentication

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.youtube.service.base.BaseViewModel
import com.example.youtube.service.repositories.UserRepository
import com.example.youtube.service.models.LiveDataStatus
import com.example.youtube.service.models.api.ResponseType


class LoginViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login() {
        if (loginDataValidate()) {
            status.value = LiveDataStatus.LOADING
            email.value?.let {
                password.value?.let { it1 ->
                    userRepository.login(it, it1) { response, message ->
                        when (response) {
                            ResponseType.RESPONSE_SUCCESSFUL -> {
                                status.value = LiveDataStatus.LOADED_SUCCESSFUL
                            }
                            ResponseType.RESPONSE_INCORRECT_DATA -> {
                                status.value = LiveDataStatus.LOADED_EMPTY
                            }
                            ResponseType.RESPONSE_UNSPECIFIED -> {
                                status.value = LiveDataStatus.ERROR
                                this.message.value = message
                            }
                            ResponseType.RESPONSE_SERVER_ERROR -> {
                                status.value = LiveDataStatus.ERROR
                                this.message.value = message
                            }
                            ResponseType.RESPONSE_NULL -> {
                                status.value = LiveDataStatus.ERROR
                                this.message.value = message
                            }
                            ResponseType.UNKNOWN_ERROR -> {
                                status.value = LiveDataStatus.ERROR
                                this.message.value = message
                            }
                        }
                    }
                }
            }
        }
        else {
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