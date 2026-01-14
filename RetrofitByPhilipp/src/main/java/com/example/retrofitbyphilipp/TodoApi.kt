package com.example.retrofitbyphilipp

import com.example.retrofitbyphilipp.model.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TodoApi {

    @GET("/todos")
    suspend fun getTodos(/*@Query("apiKey") apiKey: String*/): Response<List<Todo>>

    /*@POST("/createTodo")
    suspend fun createTodo(@Body todo: Todo): Response<CreateTodoResponse>*/  //@Body annotation will parse this "todo" parameter to json
}