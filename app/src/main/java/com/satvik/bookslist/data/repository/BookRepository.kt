package com.satvik.bookapp.data.repository

import com.satvik.bookapp.data.api.RetrofitInstance

class BookRepository {
    suspend fun getBooks(query: String) = RetrofitInstance.api.getBooks(query)
}
