package com.example.myapplication.activity

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

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