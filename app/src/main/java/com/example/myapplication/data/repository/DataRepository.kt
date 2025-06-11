package com.example.myapplication.data.repository

import android.util.Log
import com.example.myapplication.data.api.GoogleBooksApi
import com.example.myapplication.data.db.Dao
import com.example.myapplication.data.mapper.BookMapper
import com.example.myapplication.data.mapper.DiskMapper
import com.example.myapplication.data.mapper.GoogleBooksMapper
import com.example.myapplication.data.mapper.LibraryItemMapper
import com.example.myapplication.data.mapper.NewspaperMapper
import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository(
    private val libraryDao: Dao,
    private val googleBooksApi: GoogleBooksApi,
    private val mapper: GoogleBooksMapper,
    private val bookMapper: BookMapper,
    private val newspaperMapper: NewspaperMapper,
    private val diskMapper: DiskMapper
) : LibraryRepository {

    override fun observeAllItems(): Flow<List<LibraryItem>> {
        return libraryDao.observeAllItems().map { entities ->
            entities.map { entity ->
                LibraryItemMapper.toDomain(entity)
            }
        }
    }

    override suspend fun getAllItemsSortedByName(offset: Int, limit: Int): List<LibraryItem> {
        val books = libraryDao.getBooksSortedByName(offset, limit).map { bookMapper.toDomain(it) }
        val newspapers = libraryDao.getNewspapersSortedByName(offset, limit).map { newspaperMapper.toDomain(it) }
        val disks = libraryDao.getDisksSortedByName(offset, limit).map { diskMapper.toDomain(it) }
        return (books + newspapers + disks).sortedBy { it.name }
    }

    override suspend fun getAllItemsSortedByDate(offset: Int, limit: Int): List<LibraryItem> {
        val books = libraryDao.getBooksSortedByDate(offset, limit).map { bookMapper.toDomain(it) }
        val newspapers = libraryDao.getNewspapersSortedByDate(offset, limit).map { newspaperMapper.toDomain(it) }
        val disks = libraryDao.getDisksSortedByDate(offset, limit).map { diskMapper.toDomain(it) }
        return (books + newspapers + disks).sortedBy { it.addedDate }
    }

    override suspend fun insertItem(item: LibraryItem) {
        when(item) {
            is com.example.myapplication.domain.model.Book ->
                libraryDao.insertBook(bookMapper.toData(item))
            is com.example.myapplication.domain.model.Newspaper ->
                libraryDao.insertNewspaper(newspaperMapper.toData(item))
            is com.example.myapplication.domain.model.Disk ->
                libraryDao.insertDisk(diskMapper.toData(item))
        }
    }

    override suspend fun deleteItem(item: LibraryItem) {
        when(item) {
            is com.example.myapplication.domain.model.Book ->
                libraryDao.deleteBook(bookMapper.toData(item))
            is com.example.myapplication.domain.model.Newspaper ->
                libraryDao.deleteNewspaper(newspaperMapper.toData(item))
            is com.example.myapplication.domain.model.Disk ->
                libraryDao.deleteDisk(diskMapper.toData(item))
        }
    }

    override suspend fun getTotalCount(): Int {
        val booksCount = libraryDao.getBooksCount()
        val newspapersCount = libraryDao.getNewspapersCount()
        val disksCount = libraryDao.getDisksCount()
        return booksCount + newspapersCount + disksCount
    }

    override suspend fun searchGoogleBooks(title: String?, author: String?): List<LibraryItem> {
        val query = buildString {
            title?.let { append("intitle:$it") }
            author?.let {
                if (isNotEmpty()) append("+")
                append("inauthor:$it")
            }
        }

        return try {
            val response = googleBooksApi.searchBooks(query)
            response.items?.mapNotNull { mapper.toLibraryItem(it) } ?: emptyList()
        } catch (e: Exception) {
            throw Exception("Ошибка поиска: ${e.message}")
        }
    }
}