package com.example.retrofitexample

data class QuoteList(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int     // Order of properties doesn't matter, only the name of the properties should be same as json properties of api response
)