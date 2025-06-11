package com.example.myapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Query


interface GoogleBooksApi {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("fields") fields: String = "items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/pageCount)"
    ): GoogleBooksResponse
}