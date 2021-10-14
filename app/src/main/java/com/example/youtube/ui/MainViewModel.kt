package com.example.youtube.ui

import com.example.youtube.service.base.BaseViewModel
import com.example.youtube.service.models.LiveDataStatus
import com.example.youtube.service.models.api.ResponseType
import com.example.youtube.service.repositories.UserRepository

class MainViewModel(private val userRepository: UserRepository) : BaseViewModel() {

    fun firstCheck() {
        status.value = LiveDataStatus.LOADING

        userRepository.checkCurrentUser { response, message ->
            when (response) {
                ResponseType.RESPONSE_SUCCESSFUL -> {
                    status.value = LiveDataStatus.LOADED_SUCCESSFUL
                }
                ResponseType.RESPONSE_INCORRECT_DATA -> {
                    status.value = LiveDataStatus.LOADED_EMPTY
                }
                else -> {
                    status.value = LiveDataStatus.ERROR
                    this.message.value = message
                }
            }
        }
    }
}