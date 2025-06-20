package com.example.myapplication.presentation.fragment

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.domain.model.Month
import com.example.myapplication.domain.model.Book
import com.example.myapplication.domain.model.Disk
import com.example.myapplication.domain.model.Newspaper
import com.example.myapplication.databinding.FragmentItemBinding

class DisplayAddItem(private val binding: FragmentItemBinding) {
    fun display(itemType: String) {
        when (itemType) {
            "Book" -> {
                displayBook()
            }

            "Newspaper" -> {
                displayNewspaper()
            }

            "Disk" -> {
                displayDisk()
            }

            else -> throw IllegalArgumentException("неверный тип")
        }
    }

    fun createItem(itemType: String): Any? {
        val resources = binding.root.context.resources
        val name = binding.editName.text.toString().takeIf { it.isNotBlank() } ?: run {
            binding.editName.error = resources.getString(R.string.error_enter_name)
            return null
        }

        val access = binding.editAccess.text.toString() == resources.getString(R.string.available)

        return when (itemType) {
            "Book" -> {
                val pages =
                    binding.editOptional.text.toString().toIntOrNull()?.takeIf { it > 0 } ?: run {
                        binding.editOptional.error = resources.getString(R.string.error_enter_pages)
                        return null
                    }
                val author =
                    binding.editSecondOptional.text.toString().takeIf { it.isNotBlank() } ?: run {
                        binding.editSecondOptional.error =
                            resources.getString(R.string.error_enter_author)
                        return null
                    }
                Book(null, name, access, "book", System.currentTimeMillis(), author, pages)
            }

            "Newspaper" -> {
                val number = binding.editOptional.text.toString().toIntOrNull() ?: run {
                    binding.editOptional.error = resources.getString(R.string.error_enter_number)
                    return null
                }
                val month = try {
                    Month.valueOf(binding.editSecondOptional.text.toString())
                } catch (e: IllegalArgumentException) {
                    binding.editSecondOptional.error =
                        resources.getString(R.string.error_enter_month)
                    return null
                }
                Newspaper(null, name, access, "newspaper", System.currentTimeMillis(),number, month)
            }

            "Disk" -> {
                val type = binding.editOptional.text.toString().takeIf { it.isNotBlank() } ?: run {
                    binding.editOptional.error = resources.getString(R.string.error_enter_type)
                    return null
                }
                Disk(null, name, access, "disk", System.currentTimeMillis(), type)
            }

            else -> null
        }
    }

    private fun displayBook() {
        val resources = binding.root.context.resources
        binding.apply {
            itemTextOptionally.text = resources.getString(R.string.book_pages)
            itemTextSecondOptional.text = resources.getString(R.string.book_author)
            editOptional.hint = resources.getString(R.string.hint_enter_pages)
            editOptional.inputType = InputType.TYPE_CLASS_NUMBER
            editOptional.keyListener = DigitsKeyListener.getInstance("0123456789")
            editSecondOptional.hint = resources.getString(R.string.hint_enter_author)
        }
    }

    private fun displayNewspaper() {
        val resources = binding.root.context.resources
        binding.apply {
            itemTextOptionally.text = resources.getString(R.string.newspaper_number)
            editOptional.inputType = InputType.TYPE_CLASS_NUMBER
            editOptional.keyListener = DigitsKeyListener.getInstance("0123456789")
            itemTextSecondOptional.text = resources.getString(R.string.newspaper_month)
            editOptional.hint = resources.getString(R.string.hint_enter_number)
            editSecondOptional.hint = resources.getString(R.string.hint_enter_month)
        }
    }

    private fun displayDisk() {
        val resources = binding.root.context.resources
        binding.apply {
            itemTextOptionally.text = resources.getString(R.string.disk_type)
            itemTextSecondOptional.visibility = View.GONE
            editOptional.hint = resources.getString(R.string.hint_enter_type)
            editSecondOptional.visibility = View.GONE
        }
    }

}