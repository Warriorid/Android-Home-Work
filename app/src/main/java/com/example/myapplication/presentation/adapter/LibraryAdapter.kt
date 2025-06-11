package com.example.myapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.databinding.ItemTemplateBinding
import com.example.myapplication.common.util.LibraryItemDiffUtil


class LibraryAdapter(
    private val onClickItem: (LibraryItem) -> Unit,
    private val onLongClickItem: (LibraryItem) -> Unit,
    private val onSwipeToDelete: (LibraryItem) -> Unit
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

        holder.itemView.setOnLongClickListener {
            item?.let { onLongClickItem(it) }
            true
        }
    }

    fun notifyItemSwiped(position: Int) {
        getItem(position)?.let { onSwipeToDelete(it) }
    }

}