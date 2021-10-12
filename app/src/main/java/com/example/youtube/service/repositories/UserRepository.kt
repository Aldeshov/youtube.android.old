package com.example.youtube.service.repositories

import com.example.youtube.service.models.LoginResponseInfo
import com.example.youtube.service.models.UserResponseInfo


interface UserRepository {
    fun checkCurrentUser(onResult: (isSuccess: Boolean, response: UserResponseInfo?) -> Unit)

    fun login(email: String, password: String, onResult: (isSuccess: Boolean, response: LoginResponseInfo?) -> Unit)
}