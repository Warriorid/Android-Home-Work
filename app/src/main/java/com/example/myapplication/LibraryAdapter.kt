package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.databinding.ItemTemplateBinding


class LibraryAdapter : ListAdapter<LibraryItem, LibraryViewHolder>(LibraryItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = ItemTemplateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LibraryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            item?.let {
                context.startActivity(ItemActivityNavigator.createIntent(context, item))
            }

        }
    }

    fun updateItems(newItems: List<LibraryItem>) {
        submitList(newItems.toList())
    }

    fun removeItem(position: Int): LibraryItem? {
        val item = currentList.getOrNull(position) ?: return null
        val newList = currentList.toMutableList().apply { removeAt(position) }
        submitList(newList)
        return item
    }

}