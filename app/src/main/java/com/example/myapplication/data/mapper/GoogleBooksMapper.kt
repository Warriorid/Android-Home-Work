package com.example.myapplication.data.mapper

import com.example.myapplication.data.api.GoogleBookItem
import com.example.myapplication.domain.model.Book
import com.example.myapplication.domain.model.LibraryItem

class GoogleBooksMapper {
    fun toLibraryItem(item: GoogleBookItem): LibraryItem? {
        val volumeInfo = item.volumeInfo ?: return null
        return Book(
            name = volumeInfo.title ?: "No title",
            author = volumeInfo.authors?.joinToString() ?: "Unknown author",
            pages = volumeInfo.pageCount ?: 0,
            access = true,
            addedDate = System.currentTimeMillis()
        )
    }
}