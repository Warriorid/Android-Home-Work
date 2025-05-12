package com.example.myapplication.data.repository

import com.example.myapplication.data.api.GoogleBooksApi
import com.example.myapplication.data.db.Dao
import com.example.myapplication.data.mapper.BookMapper
import com.example.myapplication.data.mapper.DiskMapper
import com.example.myapplication.data.mapper.GoogleBooksMapper
import com.example.myapplication.data.mapper.NewspaperMapper
import com.example.myapplication.domain.model.Book
import com.example.myapplication.domain.model.Disk
import com.example.myapplication.domain.model.Newspaper
import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.domain.repository.LibraryRepository


class DataRepository(
    private val libraryDao: Dao,
    private val googleBooksApi: GoogleBooksApi,
    private val mapper: GoogleBooksMapper
) : LibraryRepository {

    override suspend fun getAllItemsSortedByName(offset: Int, limit: Int): List<LibraryItem> {
        val books = libraryDao.getBooksSortedByName(offset, limit)
        val newspapers = libraryDao.getNewspapersSortedByName(offset, limit)
        val disks = libraryDao.getDisksSortedByName(offset, limit)
        return (books + newspapers + disks).sortedBy { it.name }
    }

    override suspend fun getAllItemsSortedByDate(offset: Int, limit: Int): List<LibraryItem> {
        val books = libraryDao.getBooksSortedByDate(offset, limit)
        val newspapers = libraryDao.getNewspapersSortedByDate(offset, limit)
        val disks = libraryDao.getDisksSortedByDate(offset, limit)
        return (books + newspapers + disks).sortedBy { it.addedDate }
    }

    override suspend fun insertItem(item: LibraryItem) {
        when(item) {
            is Book ->
                libraryDao.insertBook(BookMapper.toData(item))
            is Newspaper ->
                libraryDao.insertNewspaper(NewspaperMapper.toData(item))
            is Disk ->
                libraryDao.insertDisk(DiskMapper.toData(item))
        }
    }

    override suspend fun deleteItem(item: LibraryItem) {
        when(item) {
            is Book -> {
                val bookEntity = BookMapper.toData(item)
                libraryDao.deleteBook(bookEntity)
            }
            is Newspaper -> {
                val newspaperEntity = NewspaperMapper.toData(item)
                libraryDao.deleteNewspaper(newspaperEntity)
            }
            is Disk -> {
                val diskEntity = DiskMapper.toData(item)
                libraryDao.deleteDisk(diskEntity)
            }
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