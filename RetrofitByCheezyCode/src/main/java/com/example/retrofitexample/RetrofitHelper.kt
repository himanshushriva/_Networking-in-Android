package com.example.retrofitexample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    private val BASE_URL = "https://api.quotable.io/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(UnsafeOkHttpClient.getUnsafeOkHttpClient())         // Warning: This should **never** be used in production, as it completely bypasses HTTPS security and leaves the app vulnerable to attacks.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}