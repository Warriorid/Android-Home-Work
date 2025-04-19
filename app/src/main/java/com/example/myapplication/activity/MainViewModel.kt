package com.example.myapplication.activity


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.data.DataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import kotlin.random.Random

class MainViewModel() : ViewModel() {

    private val _item = MutableStateFlow<List<LibraryItem>>(emptyList())
    val item: StateFlow<List<LibraryItem>> = _item
    private val _selectedItem = MutableLiveData<LibraryItem?>(null)
    val selectedItem: LiveData<LibraryItem?> = _selectedItem
    private val _itemType = MutableLiveData<String?>(null)
    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> = _loading


    init {
        loadData()
    }

    private fun loadData() {
        val randomTime = (1000..3000).random().toLong()
        _loading.value = true
        viewModelScope.launch {
            delay(randomTime)
            try {
                if (Random.nextInt(3) == 0) throw IOException("Невозможно загрузить данные")
                _item.value += DataRepository.listOfAllTypes
            } catch (e: Exception) {
                _error.emit("Неудалось загрузить данные")
            } finally {
                _loading.value = false
            }
        }

    }

    fun updateItems(item: LibraryItem) {
        val oldList = _item.value
        val list = listOf(item)
        _item.value = oldList.plus(list)
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


    fun removeItems(itemsToRemove: List<LibraryItem>) {
        val currentList = _item.value
        _item.value = currentList.minus(itemsToRemove)
    }
}