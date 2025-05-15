package com.example.myapplication.presentation.fragment

import android.view.View
import com.example.myapplication.R
import com.example.myapplication.data.model.Book
import com.example.myapplication.data.model.Disk
import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.data.model.Newspaper
import com.example.myapplication.databinding.FragmentItemBinding

class DisplayItems(private val binding: FragmentItemBinding) {
    fun displayItem(item: LibraryItem) {
        binding.apply {
            textItemName.text = item.name
            itemTextIdResult.text = item.id?.toString() ?: ""
            itemTextAccessResult.text = if (item.access) "В наличии" else "Нет в наличии"

            saveButtom.visibility = View.GONE
            editName.visibility = View.GONE
            editId.visibility = View.GONE
            editAccess.visibility = View.GONE
            editOptional.visibility = View.GONE
            editSecondOptional.visibility = View.GONE

            try {
                when (item.itemType) {
                    "book" -> displayBook(item as com.example.myapplication.domain.model.Book)
                    "newspaper" -> displayNewspaper(item as com.example.myapplication.domain.model.Newspaper)
                    "disk" -> displayDisk(item as com.example.myapplication.domain.model.Disk)
                    else -> throw IllegalArgumentException("UnknownItemType")
                }
            } catch (e: ClassCastException) {
                throw IllegalArgumentException("UnknownItemType")
            }
        }
    }

    private fun displayBook(book: com.example.myapplication.domain.model.Book) {
        binding.apply {
            imageItem.setImageResource(R.drawable.book_avatar)
            itemTextOptionally.text = "Страниц:"
            itemTextOptionallyResult.text = book.pages.toString()
            itemTextSecondOptional.text = "Автор:"
            itemTextSecondOptionalResult.text = book.author
        }
    }

    private fun displayNewspaper(newspaper: com.example.myapplication.domain.model.Newspaper) {
        binding.apply {
            imageItem.setImageResource(R.drawable.newspaper_avatar)
            itemTextOptionally.text = "Номер:"
            itemTextOptionallyResult.text = newspaper.number.toString()
            itemTextSecondOptional.text = "Месяц:"
            itemTextSecondOptionalResult.text = newspaper.month.toString()
        }
    }

    private fun displayDisk(disk: com.example.myapplication.domain.model.Disk) {
        binding.apply {
            imageItem.setImageResource(R.drawable.disk_avatar)
            itemTextOptionally.text = "Тип:"
            itemTextOptionallyResult.text = disk.diskType
            itemTextSecondOptional.visibility = View.GONE
            itemTextSecondOptionalResult.visibility = View.GONE
        }
    }

}