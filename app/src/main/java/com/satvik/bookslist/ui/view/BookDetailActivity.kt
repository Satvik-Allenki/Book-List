package com.satvik.bookapp.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


import com.satvik.bookslist.databinding.ActivityBookDetailBinding


class BookDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val desc = intent.getStringExtra("desc")
        val image = intent.getStringExtra("image")

        binding.tvTitle.text = title
        binding.tvAuthor.text = author
        binding.tvDescription.text = desc

        Glide.with(this).load(image).into(binding.ivThumbnail)
    }
}
