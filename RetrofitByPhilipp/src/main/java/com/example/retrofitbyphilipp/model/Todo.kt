package com.example.retrofitbyphilipp.model

data class Todo(
    val completed: Boolean,
    val title: String,
    val id: Int,
    val userId: Int
)