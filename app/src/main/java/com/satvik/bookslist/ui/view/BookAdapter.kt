package com.satvik.bookapp.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.satvik.bookapp.data.model.BookItem

import com.satvik.bookslist.databinding.ItemBookBinding

class BookAdapter(
    private var books: List<BookItem>,
    private val onItemClick: (BookItem) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        holder.binding.tvTitle.text = book.volumeInfo.title ?: "No Title"
        holder.binding.tvAuthor.text = book.volumeInfo.authors?.joinToString(", ") ?: "Unknown Author"

        // Load image with Glide
        Glide.with(holder.itemView.context)
            .load(book.volumeInfo.imageLinks?.thumbnail)
            .into(holder.binding.ivThumbnail)

        // Click listener
        holder.itemView.setOnClickListener { onItemClick(book) }
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<BookItem>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
