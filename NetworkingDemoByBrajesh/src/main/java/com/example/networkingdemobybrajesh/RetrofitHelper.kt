package com.example.networkingdemobybrajesh

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    const val BASE_URL = "https://api.chucknorris.io/"

    fun <S> createRetrofitService(context: Context, serviceClass: Class<S>): S {
        return buildRetrofit(context).create(serviceClass)
    }

    fun buildRetrofit(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}