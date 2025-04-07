package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {

    private val _item = MutableLiveData<List<LibraryItem>>()
    val item: LiveData<List<LibraryItem>> = _item

    fun updateItems(list: List<LibraryItem>) {
        val oldList = _item.value
        _item.value = oldList?.plus(list) ?: list

    }

    fun removeItems(itemsToRemove: List<LibraryItem>) {
        val currentList = _item.value.orEmpty()
        _item.value = currentList.filterNot { item ->
            itemsToRemove.any { it.id == item.id }
        }
    }
}