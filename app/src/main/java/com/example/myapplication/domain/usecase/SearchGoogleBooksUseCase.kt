package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.domain.repository.LibraryRepository
import javax.inject.Inject

class SearchGoogleBooksUseCase @Inject constructor(
    private val repository: LibraryRepository
) {
    suspend operator fun invoke(title: String?, author: String?): List<LibraryItem> {
        return repository.searchGoogleBooks(title, author)
    }
}