package com.example.myapplication.activity

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.data.LibraryItem

class LibraryItemDiffUtil : DiffUtil.ItemCallback<LibraryItem>() {
    override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.access == newItem.access
    }

}