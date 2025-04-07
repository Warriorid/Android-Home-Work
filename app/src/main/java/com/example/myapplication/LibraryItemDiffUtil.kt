package com.example.myapplication

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class LibraryItemDiffUtil : DiffUtil.ItemCallback<LibraryItem>() {
    override fun areItemsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: LibraryItem, newItem: LibraryItem): Boolean {
        return oldItem == newItem
    }

}