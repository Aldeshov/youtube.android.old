package com.example.youtube.service.repositories

import com.example.youtube.database.Database
import com.example.youtube.service.models.LoginResponseInfo
import com.example.youtube.service.models.UserResponseInfo
import com.example.youtube.service.models.authentication.LoginResponse
import com.example.youtube.service.models.authentication.User
import com.example.youtube.service.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(
    private val api: APIService,
    private val db: Database
): UserRepository {
    override fun checkCurrentUser(onResult: (isSuccess: Boolean, response: UserResponseInfo?) -> Unit) {
        api.getUser().enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        // Updating User Information
                        val user = db.userDao().getUser()!!
                        user.email = response.body()!!.email
                        user.full_name = response.body()!!.full_name
                        user.avatar = response.body()!!.avatar
                        db.userDao().update(user)
                        onResult(true, UserResponseInfo.SUCCESSFUL)
                    }
                    if (response.code() in 300..399)
                        onResult(true, UserResponseInfo.UNSPECIFIED_RESPONSE)
                    if (response.code() in 400..499) {
                        // Removing User Information
                        val user = db.userDao().getUser()!!
                        user.email = null
                        user.full_name = null
                        user.avatar = null
                        user.token = null
                        db.userDao().update(user)

                        onResult(true, UserResponseInfo.INCORRECT_DATA)
                    }
                    if (response.code() in 500..599)
                        onResult(true, UserResponseInfo.SERVER_ERROR)
                }
                else
                    onResult(false, UserResponseInfo.UNKNOWN_ERROR)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onResult(false, UserResponseInfo.UNKNOWN_ERROR)
            }
        })
    }

    override fun login(
        email: String,
        password: String,
        onResult: (isSuccess: Boolean, response: LoginResponseInfo?) -> Unit) {
        api.login(email, password).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        // Updating User Information
                        val user = db.userDao().getUser()!!
                        user.token = response.body()!!.token
                        db.userDao().update(user)
                        onResult(true, LoginResponseInfo.LOGGED_IN)
                    }
                    if (response.code() in 300..399)
                        onResult(true, LoginResponseInfo.UNSPECIFIED_RESPONSE)
                    if (response.code() in 400..499)
                        onResult(true, LoginResponseInfo.INCORRECT_DATA)
                    if (response.code() in 500..599)
                        onResult(true, LoginResponseInfo.SERVER_ERROR)
                }
                else
                    onResult(false, LoginResponseInfo.UNKNOWN_ERROR)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(false, LoginResponseInfo.UNKNOWN_ERROR)
            }
        })
    }
}