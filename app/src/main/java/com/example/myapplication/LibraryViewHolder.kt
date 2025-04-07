package com.example.myapplication

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTemplateBinding

class LibraryViewHolder(private val binding: ItemTemplateBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: LibraryItem) {
        binding.itemTitle.text = "${item.name} id: ${item.id}"

        when (item) {
            is Book -> binding.imageView.setImageResource(R.drawable.book_avatar)
            is Newspaper -> binding.imageView.setImageResource(R.drawable.newspaper_avatar)
            is Disk -> binding.imageView.setImageResource(R.drawable.disk_avatar)
        }
    }
}