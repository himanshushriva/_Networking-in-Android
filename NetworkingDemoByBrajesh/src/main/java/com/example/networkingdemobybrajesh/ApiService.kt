package com.example.networkingdemobybrajesh

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {

    //GET request with a url in suspend function parameter with @Url annotation
    @GET
    suspend fun getRandomJoke(
        @Url url: String
    ) : Response<Joke>


    //GET request with a url in GET parameter and without @Url annotation unlike above
    @GET("jokes/random")
    //@GET("https://api.chucknorris.io/jokes/random")   //Both will work.
    suspend fun getANewJoke() : Response<Joke>
}