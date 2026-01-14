package com.example.retrofitbyandroidknowledge

import retrofit2.Response
import retrofit2.http.GET


interface AlbumService {

    @GET("albums")
    suspend fun getAlbum(): Response<Albums/*List<AlbumItem>*/>
}