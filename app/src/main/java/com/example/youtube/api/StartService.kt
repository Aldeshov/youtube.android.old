package com.example.youtube.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.youtube.configs.BASE_URL
import com.example.youtube.database.App
import com.example.youtube.database.Database
import com.example.youtube.database.UserDao
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class StartService {
    private var db: Database = App.instance.getDatabase()!!

    companion object {
        var coreService = Any() // Requires Starting Service
        var  userDao: UserDao? = null
    }

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        userDao = db.userDao()

        val token = userDao!!.getUser()?.token

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "JWT $token")
                    .build()
                chain.proceed(newRequest)
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        coreService = retrofit.create(APIService::class.java)
    }
}
