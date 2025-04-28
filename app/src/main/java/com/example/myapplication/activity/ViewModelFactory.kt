package com.example.myapplication.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.DataRepository

class ViewModelFactory(private val repository: DataRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(repository) as T
            else -> throw IllegalArgumentException("Такой VM нет")
        }
    }
}