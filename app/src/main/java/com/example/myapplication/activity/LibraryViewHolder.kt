package com.example.myapplication.activity

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.LibraryItem
import com.example.myapplication.databinding.ItemTemplateBinding

class LibraryViewHolder(private val binding: ItemTemplateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LibraryItem) {
        binding.itemTitle.text = "${item.name} id: ${item.id}"

        val image = when (item.itemType) {
            "book" -> R.drawable.book_avatar
            "newspaper" -> R.drawable.newspaper_avatar
            "disk" -> R.drawable.disk_avatar
            else -> throw IllegalArgumentException("такого типа нет")
        }
        binding.imageView.setImageResource(image)
    }
} 