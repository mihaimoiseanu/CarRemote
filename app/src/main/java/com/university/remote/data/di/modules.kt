package com.university.remote.data.di

import com.google.gson.Gson
import com.university.remote.data.net.WebSocketClient
import com.university.remote.data.repository.CarRemoteRepository
import com.university.remote.domain.repository.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val dataModule = module {
    single<Repository>{
        CarRemoteRepository(get(), get())
    }
}

val networkModule = module {

    single{
        OkHttpClient
            .Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()
    }

    single{
        Gson()
    }

    single {
        WebSocketClient(get())
    }
}