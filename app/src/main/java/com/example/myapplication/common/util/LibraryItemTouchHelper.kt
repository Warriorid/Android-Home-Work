package com.example.myapplication.common.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.presentation.adapter.LibraryAdapter
import com.example.myapplication.presentation.viewmodel.MainViewModel

class LibraryItemTouchHelper(
    private val adapter: LibraryAdapter,
    private val viewModel: MainViewModel
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val removedItem = adapter.removeItem(position)
        removedItem?.let { viewModel.removeItems(listOf(it), viewModel.selectedItem.value) }
    }

}