package com.example.youtube.api

import com.example.youtube.models.VideoContent
import com.example.youtube.models.authentication.LoginResponse
import com.example.youtube.models.authentication.User
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @Headers(*["Cache-Control: max-age=640000", "User-Agent: YouTube"])
    @GET("app/contents")
    fun getVideoContents(): Call<ArrayList<VideoContent>>

    @GET("auth/users/me")
    fun getUser(): Call<User>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}
