package com.example.myapplication.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.data.LibraryItem
import com.example.myapplication.databinding.ItemTemplateBinding


class LibraryAdapter(
    private val onClickItem: (LibraryItem) -> Unit
) : ListAdapter<LibraryItem, LibraryViewHolder>(LibraryItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding =
            ItemTemplateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            item?.let { onClickItem(it) }
        }
    }

    fun removeItem(position: Int): LibraryItem? {
        val item = currentList.getOrNull(position) ?: return null
        val newList = currentList.toMutableList().apply { removeAt(position) }
        submitList(newList)
        return item
    }

}