package com.example.youtube.service.repositories

import com.example.youtube.service.models.api.ResponseType


interface UserRepository {
    fun checkCurrentUser(
        onResult: (response: ResponseType, message: String?) -> Unit
    )

    fun login(
        email: String,
        password: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    )
}