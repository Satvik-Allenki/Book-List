package com.satvik.bookapp.data.api

import com.satvik.bookapp.data.model.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String
    ): Response<BookResponse>
}
