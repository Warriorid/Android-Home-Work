package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.domain.repository.LibraryRepository
import javax.inject.Inject

class GetItemsSortedByNameUseCase @Inject constructor(
    private val repository: LibraryRepository
) {
    suspend operator fun invoke(offset: Int, limit: Int): List<LibraryItem> {
        return repository.getAllItemsSortedByName(offset, limit)
    }
}