package com.example.youtube

import androidx.lifecycle.MutableLiveData
import com.example.youtube.service.models.LiveDataInfo
import com.example.youtube.service.models.UserResponseInfo
import com.example.youtube.service.repositories.UserRepository

class MainPresenter(private val userRepository: UserRepository) {
    val status = MutableLiveData<LiveDataInfo>().apply { value = LiveDataInfo.LOADING }

    fun firstCheck() {
        status.value = LiveDataInfo.LOADING
        userRepository.checkCurrentUser { isSuccess, response ->
            if (isSuccess) {
                when (response) {
                    UserResponseInfo.SUCCESSFUL -> {
                        status.value = LiveDataInfo.LOADED_SUCCESSFUL
                    }
                    UserResponseInfo.INCORRECT_DATA -> {
                        status.value = LiveDataInfo.LOADED_EMPTY
                    }
                    else -> {
                        status.value = LiveDataInfo.ERROR
                    }
                }
            }
            else
                status.value = LiveDataInfo.ERROR
        }
    }
}