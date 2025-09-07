package com.satvik.bookapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.satvik.bookslist.R


import com.satvik.bookslist.databinding.ActivityBookDetailBinding


class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title") ?: "No Title"
        val author = intent.getStringExtra("author") ?: "Unknown Author"
        val desc = intent.getStringExtra("desc") ?: "No Description"
        val image = intent.getStringExtra("image") // may be empty string

        binding.tvTitle.text = title
        binding.tvAuthor.text = author
        binding.tvDescription.text = desc

        if (!image.isNullOrBlank()) {
            Glide.with(this)
                .load(image)
                .into(binding.ivThumbnail)
        } else {
            binding.ivThumbnail.setImageResource(android.R.color.darker_gray)
            // or hide the ImageView:
            // binding.ivThumbnail.visibility = View.GONE
        }

    }
}

