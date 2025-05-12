package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.domain.repository.LibraryRepository
import javax.inject.Inject

class DeleteItemUseCase @Inject constructor(
    private val repository: LibraryRepository
) {
    suspend operator fun invoke(item: LibraryItem) {
        repository.deleteItem(item)
    }
}