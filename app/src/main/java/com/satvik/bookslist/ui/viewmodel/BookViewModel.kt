package com.satvik.bookapp.ui.viewmodel

import androidx.lifecycle.*
import com.satvik.bookapp.data.model.BookResponse
import com.satvik.bookapp.data.repository.BookRepository
import com.satvik.bookapp.utils.Resource
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _books = MutableLiveData<Resource<BookResponse>>()
    val books: LiveData<Resource<BookResponse>> get() = _books

    fun fetchBooks(query: String) {
        viewModelScope.launch {
            _books.postValue(Resource.Loading())
            try {
                val response = repository.getBooks(query)
                if (response.isSuccessful && response.body() != null) {
                    _books.postValue(Resource.Success(response.body()!!))
                } else {
                    _books.postValue(Resource.Error("Unavailable"))
                }
            } catch (e: Exception) {
                _books.postValue(Resource.Error("Error: ${e.message}"))
            }
        }
    }
}
