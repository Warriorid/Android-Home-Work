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

        val alphaValue = if (!item.access) 0.3f else 1.0f
        binding.rootLayout.alpha = alphaValue
        binding.imageView.alpha = alphaValue
        binding.itemTitle.alpha = alphaValue

        val elevationValue = if (!item.access) 1f else 10f
        binding.rootLayout.elevation = elevationValue
        binding.imageView.elevation = elevationValue
    }
}