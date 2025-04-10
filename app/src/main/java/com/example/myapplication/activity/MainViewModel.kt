package com.example.myapplication.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.consoleapp.LibraryItem

class MainViewModel() : ViewModel() {

    private val _item = MutableLiveData<List<LibraryItem>>()
    val item: LiveData<List<LibraryItem>> = _item

    fun updateItems(list: List<LibraryItem>) {
        val oldList = _item.value
        _item.value = oldList?.plus(list) ?: list
    }

    fun removeItems(itemsToRemove: List<LibraryItem>) {
        val currentList = _item.value
        _item.value = currentList?.minus(itemsToRemove)
    }
}