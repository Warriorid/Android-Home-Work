package com.example.myapplication.activity

import android.view.View
import com.example.myapplication.consoleapp.Book
import com.example.myapplication.consoleapp.Disk
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.consoleapp.Newspaper
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityItemBinding

class DisplayItems(private val binding: ActivityItemBinding) {
    fun displayItem(item: LibraryItem) {
        binding.apply {
            textItemName.text = item.name
            itemTextIdResult.text = item.id.toString()
            itemTextAccessResult.text = if (item.access) "В наличии" else "Нет в наличии"
            saveButtom.visibility = View.GONE
            editName.visibility = View.GONE
            editId.visibility = View.GONE
            editAccess.visibility = View.GONE
            editOptional.visibility = View.GONE
            editSecondOptional.visibility = View.GONE

            when (item) {
                is Book -> displayBook(item)
                is Newspaper -> displayNewspaper(item)
                is Disk -> displayDisk(item)
            }
        }
    }

    private fun displayBook(book: Book) {
        binding.apply {
            imageItem.setImageResource(R.drawable.book_avatar)
            itemTextOptionally.text = "Страниц:"
            itemTextOptionallyResult.text = book.count.toString()
            itemTextSecondOptional.text = "Автор:"
            itemTextSecondOptionalResult.text = book.author
        }
    }

    private fun displayNewspaper(newspaper: Newspaper) {
        binding.apply {
            imageItem.setImageResource(R.drawable.newspaper_avatar)
            itemTextOptionally.text = "Номер:"
            itemTextOptionallyResult.text = newspaper.number.toString()
            itemTextSecondOptional.text = "Месяц:"
            itemTextSecondOptionalResult.text = newspaper.month.toString()
        }
    }

    private fun displayDisk(disk: Disk) {
        binding.apply {
            imageItem.setImageResource(R.drawable.disk_avatar)
            itemTextOptionally.text = "Тип:"
            itemTextOptionallyResult.text = disk.type
            itemTextSecondOptional.visibility = View.GONE
            itemTextSecondOptionalResult.visibility = View.GONE
        }
    }

}