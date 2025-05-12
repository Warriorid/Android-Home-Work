package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.LibraryRepository
import javax.inject.Inject


class GetTotalCountUseCase @Inject constructor(
    private val repository: LibraryRepository
) {
    suspend operator fun invoke(): Int {
        return repository.getTotalCount()
    }
}