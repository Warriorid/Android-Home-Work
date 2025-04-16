package com.example.myapplication.activity

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.consoleapp.Book
import com.example.myapplication.consoleapp.Disk
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.consoleapp.Newspaper
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemTemplateBinding

class LibraryViewHolder(private val binding: ItemTemplateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LibraryItem) {
        binding.itemTitle.text = "${item.name} id: ${item.id}"

        val image = when (item) {
            is Book -> R.drawable.book_avatar
            is Newspaper -> R.drawable.newspaper_avatar
            is Disk -> R.drawable.disk_avatar
            else -> throw IllegalArgumentException("такого типа нет")
        }
        binding.imageView.setImageResource(image)
    }
} 