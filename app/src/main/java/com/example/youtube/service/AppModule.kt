package com.example.youtube.service

import com.example.youtube.BASE_URL
import com.example.youtube.YouTube
import com.example.youtube.database.Database
import com.example.youtube.service.network.APIService
import com.example.youtube.service.network.AuthInterceptor
import com.example.youtube.service.repositories.ContentRepository
import com.example.youtube.service.repositories.ContentRepositoryImpl
import com.example.youtube.service.repositories.UserRepository
import com.example.youtube.service.repositories.UserRepositoryImpl
import com.example.youtube.ui.MainViewModel
import com.example.youtube.ui.authentication.LoginViewModel
import com.example.youtube.ui.general.ListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    fun apiProvider(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    factory { apiProvider(get()) }

    single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<ContentRepository> { ContentRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { ListViewModel(get()) }
}

val dbModule = module {
    fun dbProvider(): Database? {
        return YouTube.instance.getDatabase()
    }

    single { dbProvider() }
}

val apiModule = module {
    fun loggingInterceptorProvider(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return loggingInterceptor
    }

    fun httpClientProvider(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    fun retrofitProvider(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    single { loggingInterceptorProvider() }
    factory { AuthInterceptor(get()) }
    factory { httpClientProvider(get(), get()) }
    single { retrofitProvider(get()) }
}