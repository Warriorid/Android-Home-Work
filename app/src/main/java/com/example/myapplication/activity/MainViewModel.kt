package com.example.myapplication.activity

import android.content.res.Configuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.data.DataRepository

class MainViewModel() : ViewModel() {

    private val _item = MutableLiveData<List<LibraryItem>>(emptyList())
    val item: LiveData<List<LibraryItem>> = _item
    private val _selectedItem = MutableLiveData<LibraryItem?>(null)
    val selectedItem: LiveData<LibraryItem?> = _selectedItem
    private val _itemType = MutableLiveData<String?>(null)
    private val _closeFragment = MutableLiveData<Boolean>(false)
    val closeFragment: LiveData<Boolean> = _closeFragment


    init {
        loadData()
    }

    private fun loadData() {
        _item.value = DataRepository.listOfAllTypes
    }

    fun updateItems(item: LibraryItem) {
        val oldList = _item.value
        val list = listOf(item)
        _item.value = oldList?.plus(list) ?: list
    }

    fun selectItem(item: LibraryItem) {
        _selectedItem.value = item
    }

    fun setItemType(type: String?) {
        _itemType.value = type
    }

    fun getItemType(): String? {
        return _itemType.value
    }

    fun clearSelectedItem() {
        _selectedItem.value = null
    }

    fun closeFragment(flag: Boolean) {
        _closeFragment.value = flag
    }


    fun removeItems(itemsToRemove: List<LibraryItem>) {
        val currentList = _item.value
        _item.value = currentList?.minus(itemsToRemove)
    }
}