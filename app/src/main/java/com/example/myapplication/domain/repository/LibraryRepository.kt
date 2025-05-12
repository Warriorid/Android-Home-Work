package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.LibraryItem

interface LibraryRepository {
    suspend fun getAllItemsSortedByName(offset: Int, limit: Int): List<LibraryItem>
    suspend fun getAllItemsSortedByDate(offset: Int, limit: Int): List<LibraryItem>
    suspend fun insertItem(item: LibraryItem)
    suspend fun deleteItem(item: LibraryItem)
    suspend fun getTotalCount(): Int
    suspend fun searchGoogleBooks(title: String?, author: String?): List<LibraryItem>
}