package com.example.myapplication.data


class DataRepository (private val libraryDao: Dao) {
    suspend fun getAllItemsSortedByName(offset: Int, limit: Int): List<LibraryItem> {
        val books = libraryDao.getBooksSortedByName(offset, limit)
        val newspapers = libraryDao.getNewspapersSortedByName(offset, limit)
        val disks = libraryDao.getDisksSortedByName(offset, limit)
        return (books + newspapers + disks).sortedBy { it.name }
    }

    suspend fun getAllItemsSortedByDate(offset: Int, limit: Int): List<LibraryItem> {
        val books = libraryDao.getBooksSortedByDate(offset, limit)
        val newspapers = libraryDao.getNewspapersSortedByDate(offset, limit)
        val disks = libraryDao.getDisksSortedByDate(offset, limit)
        return (books + newspapers + disks).sortedBy { it.addedDate }
    }

    suspend fun insertItem(item: LibraryItem) {
        when(item) {
            is Book -> libraryDao.insertBook(item)
            is Newspaper -> libraryDao.insertNewspaper(item)
            is Disk -> libraryDao.insertDisk(item)
        }
    }

    suspend fun deleteItem(item: LibraryItem) {
        when(item) {
            is Book -> libraryDao.deleteBook(item)
            is Newspaper -> libraryDao.deleteNewspaper(item)
            is Disk -> libraryDao.deleteDisk(item)
        }
    }

    suspend fun getTotalCount(): Int {
        val booksCount = libraryDao.getBooksCount()
        val newspapersCount = libraryDao.getNewspapersCount()
        val disksCount = libraryDao.getDisksCount()
        return booksCount + newspapersCount + disksCount
    }

}