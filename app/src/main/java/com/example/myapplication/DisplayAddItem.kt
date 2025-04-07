package com.example.myapplication

import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.View
import com.example.myapplication.databinding.ActivityItemBinding

class DisplayAddItem(private val binding: ActivityItemBinding) {
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

    fun createItem(itemType: String): LibraryItem? {
        val name = binding.editName.text.toString().takeIf { it.isNotBlank() } ?: run {
            binding.editName.error = "Введите название"
            return null
        }

        val id = binding.editId.text.toString().toIntOrNull() ?: run {
            binding.editId.error = "Введите числовой ID"
            return null
        }

        val access = binding.editAccess.text.toString() == "В наличии"

        return when (itemType) {
            "Book" -> {
                val pages =
                    binding.editOptional.text.toString().toIntOrNull()?.takeIf { it > 0 } ?: run {
                        binding.editOptional.error = "Введите количество страниц"
                        return null
                    }
                val author =
                    binding.editSecondOptional.text.toString().takeIf { it.isNotBlank() } ?: run {
                        binding.editSecondOptional.error = "Введите автора"
                        return null
                    }
                Book(name, id, access, pages, author)
            }

            "Newspaper" -> {
                val number = binding.editOptional.text.toString().toIntOrNull() ?: run {
                    binding.editOptional.error = "Введите номер газеты"
                    return null
                }
                val month = try {
                    Month.valueOf(binding.editSecondOptional.text.toString())
                } catch (e: IllegalArgumentException) {
                    binding.editSecondOptional.error = "Выберите месяц из списка"
                    return null
                }
                Newspaper(name, id, access, number, month)
            }

            "Disk" -> {
                val type = binding.editOptional.text.toString().takeIf { it.isNotBlank() } ?: run {
                    binding.editOptional.error = "Введите тип диска"
                    return null
                }
                Disk(name, id, access, type)
            }

            else -> null
        }
    }

    private fun displayBook() {
        binding.apply {
            itemTextOptionally.text = "Страниц"
            itemTextSecondOptional.text = "Автор"
            editOptional.hint = "Введите количество страниц"
            editOptional.inputType = InputType.TYPE_CLASS_NUMBER
            editOptional.keyListener = DigitsKeyListener.getInstance("0123456789")
            editSecondOptional.hint = "Введите автора"
        }
    }

    private fun displayNewspaper() {
        binding.apply {
            itemTextOptionally.text = "Номер"
            editOptional.inputType = InputType.TYPE_CLASS_NUMBER
            editOptional.keyListener = DigitsKeyListener.getInstance("0123456789")
            itemTextSecondOptional.text = "Месяц"
            editOptional.hint = "Введите номер"
            editSecondOptional.hint = "Введите месяц"
        }
    }

    private fun displayDisk() {
        binding.apply {
            itemTextOptionally.text = "Тип"
            itemTextSecondOptional.visibility = View.GONE
            editOptional.hint = "Введите тип"
            editSecondOptional.visibility = View.GONE
        }
    }

}