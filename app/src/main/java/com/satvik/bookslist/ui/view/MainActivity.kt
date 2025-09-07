package com.satvik.bookapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.recyclerview.widget.LinearLayoutManager
import com.satvik.bookapp.data.model.BookItem
import com.satvik.bookapp.data.repository.BookRepository

import com.satvik.bookapp.ui.viewmodel.BookViewModel
import com.satvik.bookapp.utils.Resource
import com.satvik.bookslist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: BookAdapter
    private lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel with repository
        val repository = BookRepository()
        viewModel = ViewModelProvider(this, object : Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                return BookViewModel(repository) as T
            }
        })[BookViewModel::class.java]

        // RecyclerView setup
        adapter = BookAdapter(listOf()) { book -> openBookDetails(book) }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Load initial data
        viewModel.fetchBooks("fiction")

        // Observe results (using Resource wrapper now)
        viewModel.books.observe(this) { result ->
            when (result) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    val items = result.data?.items
                    if (!items.isNullOrEmpty()) {
                        adapter.updateBooks(items)
                    } else {
                        Toast.makeText(this, "Unavailable", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(this, result.message ?: "Unavailable", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Search
        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString().trim()
            if (query.isNotEmpty()) {
                viewModel.fetchBooks(query)
            } else {
                Toast.makeText(this, "Enter search term", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openBookDetails(book: BookItem) {
        val intent = Intent(this, BookDetailActivity::class.java).apply {
            putExtra("title", book.volumeInfo.title ?: "No Title")
            putExtra("author", book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author")
            putExtra("desc", book.volumeInfo.description ?: "No Description")
            putExtra("image", book.volumeInfo.imageLinks?.thumbnail ?: "")
        }
        startActivity(intent)
    }

}
