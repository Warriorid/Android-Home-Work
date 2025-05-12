//package com.example.myapplication.domain.repository
//
//import com.example.myapplication.data.api.GoogleBookItem
//import com.example.myapplication.data.api.RetrofitHelper
//import com.example.myapplication.data.db.Dao
//import com.example.myapplication.data.model.Book
//import com.example.myapplication.data.model.Disk
//import com.example.myapplication.domain.model.LibraryItem
//import com.example.myapplication.data.model.Newspaper
//
//
//class DataRepository (private val libraryDao: Dao) {
//    suspend fun getAllItemsSortedByName(offset: Int, limit: Int): List<LibraryItem> {
//        val books = libraryDao.getBooksSortedByName(offset, limit)
//        val newspapers = libraryDao.getNewspapersSortedByName(offset, limit)
//        val disks = libraryDao.getDisksSortedByName(offset, limit)
//        return (books + newspapers + disks).sortedBy { it.name }
//    }
//
//    suspend fun getAllItemsSortedByDate(offset: Int, limit: Int): List<LibraryItem> {
//        val books = libraryDao.getBooksSortedByDate(offset, limit)
//        val newspapers = libraryDao.getNewspapersSortedByDate(offset, limit)
//        val disks = libraryDao.getDisksSortedByDate(offset, limit)
//        return (books + newspapers + disks).sortedBy { it.addedDate }
//    }
//
//    suspend fun insertItem(item: LibraryItem) {
//        when(item) {
//            is Book -> libraryDao.insertBook(item)
//            is Newspaper -> libraryDao.insertNewspaper(item)
//            is Disk -> libraryDao.insertDisk(item)
//        }
//    }
//
//    suspend fun deleteItem(item: LibraryItem) {
//        when(item) {
//            is Book -> libraryDao.deleteBook(item)
//            is Newspaper -> libraryDao.deleteNewspaper(item)
//            is Disk -> libraryDao.deleteDisk(item)
//        }
//    }
//
//    suspend fun getTotalCount(): Int {
//        val booksCount = libraryDao.getBooksCount()
//        val newspapersCount = libraryDao.getNewspapersCount()
//        val disksCount = libraryDao.getDisksCount()
//        return booksCount + newspapersCount + disksCount
//    }
//
//    suspend fun searchGoogleBooks(title: String?, author: String?): List<LibraryItem> {
//        val query = buildString {
//            title?.let { append("intitle:$it") }
//            author?.let {
//                if (isNotEmpty()) append("+")
//                append("inauthor:$it")
//            }
//        }
//
//        return try {
//            val response = RetrofitHelper.googleBooksApi.searchBooks(query)
//            response.items?.mapNotNull { it.toLibraryItem() } ?: emptyList()
//        } catch (e: Exception) {
//            throw Exception("Ошибка поиска: ${e.message}")
//        }
//    }
//
//    private fun GoogleBookItem.toLibraryItem(): LibraryItem? {
//        val volumeInfo = this.volumeInfo ?: return null
//        return Book(
//            name = volumeInfo.title ?: "No title",
//            author = volumeInfo.authors?.joinToString() ?: "Unknown author",
//            pages = volumeInfo.pageCount ?: 0,
//            access = true,
//            addedDate = System.currentTimeMillis()
//        )
//    }
//
//}