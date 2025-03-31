package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemTemplateBinding


class LibraryAdapter(private val items: MutableList<LibraryItem>) :
    RecyclerView.Adapter<LibraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val binding = ItemTemplateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LibraryViewHolder(binding).apply {
            binding.root.setOnClickListener {
                handleLibraryClick(parent.context, adapterPosition)
            }
        }
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun handleLibraryClick(context: Context, position: Int) {
        items[position].access = !items[position].access
        notifyItemChanged(position)
        Toast.makeText(context, "Элемент с id: ${items[position].id}", Toast.LENGTH_SHORT).show()
    }

    override fun getItemCount() = items.size
}