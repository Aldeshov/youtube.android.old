package com.example.youtube.service.repositories

import com.example.youtube.database.Database
import com.example.youtube.service.models.api.ResponseType
import com.example.youtube.service.models.api.authentication.User
import com.example.youtube.service.models.api.authentication.UserLogin
import com.example.youtube.service.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(
    private val api: APIService,
    private val db: Database
) : UserRepository {
    override fun checkCurrentUser(onResult: (response: ResponseType, message: String?) -> Unit) {
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

                        onResult(ResponseType.RESPONSE_SUCCESSFUL, null)
                    }
                    if (response.code() in 300..399)
                        onResult(ResponseType.RESPONSE_UNSPECIFIED, response.message())
                    if (response.code() in 400..499) {
                        // Removing User Information
                        val user = db.userDao().getUser()!!
                        user.email = null
                        user.full_name = null
                        user.avatar = null
                        user.token = null
                        db.userDao().update(user)

                        onResult(ResponseType.RESPONSE_INCORRECT_DATA, response.message())
                    }
                    if (response.code() in 500..599)
                        onResult(ResponseType.RESPONSE_SERVER_ERROR, response.message())
                } else
                    onResult(ResponseType.RESPONSE_NULL, null)
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onResult(ResponseType.UNKNOWN_ERROR, t.message)
            }
        })
    }

    override fun login(
        email: String,
        password: String,
        onResult: (response: ResponseType, message: String?) -> Unit
    ) {
        api.login(email, password).enqueue(object : Callback<UserLogin> {
            override fun onResponse(call: Call<UserLogin>, response: Response<UserLogin>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        // Updating User Information
                        val user = db.userDao().getUser()!!
                        user.token = response.body()!!.token
                        db.userDao().update(user)
                        onResult(ResponseType.RESPONSE_SUCCESSFUL, null)
                    }
                    if (response.code() in 300..399)
                        onResult(ResponseType.RESPONSE_UNSPECIFIED, response.message())
                    if (response.code() in 400..499)
                        onResult(ResponseType.RESPONSE_INCORRECT_DATA, response.message())
                    if (response.code() in 500..599)
                        onResult(ResponseType.RESPONSE_SERVER_ERROR, response.message())
                } else
                    onResult(ResponseType.RESPONSE_NULL, null)
            }

            override fun onFailure(call: Call<UserLogin>, t: Throwable) {
                onResult(ResponseType.UNKNOWN_ERROR, t.message)
            }
        })
    }
}