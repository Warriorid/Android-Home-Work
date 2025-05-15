package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.DataRepository
import com.example.myapplication.domain.model.LibraryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.math.max

class MainViewModel(private val repository: DataRepository) : ViewModel() {

    private companion object {
        const val INITIAL_PAGE_SIZE = 30
        const val LOAD_MORE_THRESHOLD = 10
        const val LOAD_PREVIOUS_THRESHOLD = 10
    }

    private val _item = MutableStateFlow<List<LibraryItem>>(emptyList())
    val item: StateFlow<List<LibraryItem>> = _item
    private val _selectedItem = MutableLiveData<LibraryItem?>(null)
    val selectedItem: LiveData<LibraryItem?> = _selectedItem
    private val _itemType = MutableLiveData<String?>(null)
    private val _sortType = MutableLiveData("date")
    private val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    private val _loading = MutableStateFlow<Boolean?>(null)
    val loading: StateFlow<Boolean?> = _loading
    private val _searchMode = MutableStateFlow<Boolean?>(false)
    val searchMode: StateFlow<Boolean?> = _searchMode
    private var _libraryLoaded = MutableStateFlow(false)
    val libraryLoaded: StateFlow<Boolean> = _libraryLoaded


    private var currentOffset = 0
    private var totalItemsCount = 0
    private var canLoadMore = true
    private var canLoadPrevious = false


    fun loadMyLibrary() {
        if (!_libraryLoaded.value) {
            _loading.value = true
            _libraryLoaded.value = true
            loadInitialData()
        }
    }
    fun clearLibraryView() {
        _item.value = emptyList()
        _libraryLoaded.value = false
    }

    fun searchBooks(title: String, author: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val result = repository.searchGoogleBooks(
                    title = if (title.length >= 3) title else null,
                    author = if (author.length >= 3) author else null
                )
                _item.value = result
            } catch (e: UnknownHostException) {
                _error.emit("Нет интернет-соединения")
            } catch (e: SocketTimeoutException) {
                _error.emit("Таймаут соединения")
            } catch (e: IOException) {
                _error.emit("Ошибка сети: ${e.message}")
            } catch (e: Exception) {
                _error.emit("Ошибка: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    private suspend fun ensureMinimumLoadingTime(startTime: Long) {
        val elapsed = System.currentTimeMillis() - startTime
        if (elapsed < 1000) {
            delay(1000 - elapsed)
        }
    }

    private fun loadInitialData() {
        _loading.value = true
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            try {
                val result = when (_sortType.value) {
                    "name" -> repository.getAllItemsSortedByName(0, INITIAL_PAGE_SIZE)
                    "date" -> repository.getAllItemsSortedByDate(0, INITIAL_PAGE_SIZE)
                    else -> emptyList()
                }

                totalItemsCount = repository.getTotalCount()
                currentOffset = 0
                canLoadMore = result.size == INITIAL_PAGE_SIZE
                canLoadPrevious = false

                _item.value = result
            } catch (e: Exception) {
                _error.emit("Не удалось загрузить данные: ${e.message}")
            } finally {
                ensureMinimumLoadingTime(startTime)
                _loading.value = false
            }
        }
    }

    private fun loadNextPage() {
        if (!canLoadMore || _loading.value == true) return

        _loading.value = true
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            try {
                val currentList = _item.value
                val newOffset = currentOffset + currentList.size
                val pageSize = INITIAL_PAGE_SIZE / 2

                val newItems = when (_sortType.value) {
                    "name" -> repository.getAllItemsSortedByName(newOffset, pageSize)
                    "date" -> repository.getAllItemsSortedByDate(newOffset, pageSize)
                    else -> emptyList()
                }

                if (newItems.isNotEmpty()) {
                    val itemsToKeep = if (currentList.size > pageSize) {
                        currentList.drop(pageSize)
                    } else {
                        currentList
                    }

                    currentOffset += (currentList.size - itemsToKeep.size)
                    canLoadMore = newItems.size == pageSize
                    canLoadPrevious = true

                    _item.value = itemsToKeep + newItems
                } else {
                    canLoadMore = false
                }
            } catch (e: Exception) {
                _error.emit("Не удалось загрузить следующие данные: ${e.message}")
            } finally {
                ensureMinimumLoadingTime(startTime)
                _loading.value = false
            }
        }
    }

    private fun loadPreviousPage() {
        if (!canLoadPrevious || _loading.value == true) return

        _loading.value = true
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            try {
                val currentList = _item.value
                val pageSize = INITIAL_PAGE_SIZE / 2
                val newOffset = max(0, currentOffset - pageSize)

                val newItems = when (_sortType.value) {
                    "name" -> repository.getAllItemsSortedByName(newOffset, pageSize)
                    "date" -> repository.getAllItemsSortedByDate(newOffset, pageSize)
                    else -> emptyList()
                }

                if (newItems.isNotEmpty()) {
                    val itemsToKeep = if (currentList.size > pageSize) {
                        currentList.dropLast(pageSize)
                    } else {
                        currentList
                    }

                    currentOffset = newOffset
                    canLoadPrevious = newOffset > 0
                    canLoadMore = true

                    _item.value = newItems + itemsToKeep
                } else {
                    canLoadPrevious = false
                }
            } catch (e: Exception) {
                _error.emit("Не удалось загрузить предыдущие данные: ${e.message}")
            } finally {
                ensureMinimumLoadingTime(startTime)
                _loading.value = false
            }
        }
    }

    fun checkPagination(position: Int) {
        val currentList = _item.value
        when {
            position >= currentList.size - LOAD_MORE_THRESHOLD -> loadNextPage()
            position <= LOAD_PREVIOUS_THRESHOLD && canLoadPrevious -> loadPreviousPage()
        }
    }


    fun sortItems(sortType: String) {
        _loading.value = true
        _item.value = emptyList()
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()
            try {
                _sortType.value = sortType
                val result = when (sortType) {
                    "name" -> repository.getAllItemsSortedByName(0, INITIAL_PAGE_SIZE)
                    "date" -> repository.getAllItemsSortedByDate(0, INITIAL_PAGE_SIZE)
                    else -> emptyList()
                }
                _item.value = result
                currentOffset = 0
                canLoadMore = result.size == INITIAL_PAGE_SIZE
                canLoadPrevious = false
            } catch (e: Exception) {
                _error.emit("Ошибка сортировки: ${e.message}")
            } finally {
                ensureMinimumLoadingTime(startTime)
                _loading.value = false
            }
        }
    }

    fun addItem(item: LibraryItem) {
        viewModelScope.launch {
            try {
                repository.insertItem(item)
                loadInitialData()
            } catch (e: Exception) {
                _error.emit("Ошибка при добавлении: ${e.message}")
            }
        }
    }

    fun removeItems(itemsToRemove: List<LibraryItem>, selectedItem: LibraryItem?) {
        viewModelScope.launch {
            try {
                itemsToRemove.forEach { item ->
                    repository.deleteItem(item)
                }
                _item.value = _item.value.minus(itemsToRemove.toSet())
                if (selectedItem != null && itemsToRemove.contains(selectedItem)) {
                    _selectedItem.value = null
                }
            } catch (e: Exception) {
                _error.emit("Ошибка при удалении: ${e.message}")
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

    fun setSearchMode(state: Boolean) {
        _searchMode.value = state
    }

    fun saveItemToDatabase(item: LibraryItem) {
        viewModelScope.launch {
            try {
                repository.insertItem(item)
                loadMyLibrary()
            } catch (e: Exception) {
                _error.emit("Ошибка сохранения: ${e.message}")
            }
        }
    }
}