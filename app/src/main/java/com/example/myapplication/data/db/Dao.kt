package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.model.Book
import com.example.myapplication.data.model.Disk
import com.example.myapplication.data.model.LibraryItemEntity
import com.example.myapplication.data.model.Newspaper
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertBook(item: Book)

    @Query("SELECT COUNT(*) FROM books")
    suspend fun getBooksCount(): Int

    @Insert
    suspend fun insertNewspaper(item: Newspaper)

    @Query("SELECT COUNT(*) FROM newspapers")
    suspend fun getNewspapersCount(): Int

    @Insert
    suspend fun insertDisk(item: Disk)

    @Query("SELECT COUNT(*) FROM disks")
    suspend fun getDisksCount(): Int

    @Delete
    suspend fun deleteBook(book: Book)

    @Delete
    suspend fun deleteNewspaper(newspaper: Newspaper)

    @Delete
    suspend fun deleteDisk(disk: Disk)

    @Query("SELECT * FROM books ORDER BY name LIMIT :limit OFFSET :offset")
    suspend fun getBooksSortedByName(offset: Int, limit: Int): List<Book>

    @Query("SELECT * FROM books ORDER BY added_date LIMIT :limit OFFSET :offset")
    suspend fun getBooksSortedByDate(offset: Int, limit: Int): List<Book>

    @Query("SELECT * FROM newspapers ORDER BY name LIMIT :limit OFFSET :offset")
    suspend fun getNewspapersSortedByName(offset: Int, limit: Int): List<Newspaper>

    @Query("SELECT * FROM newspapers ORDER BY added_date LIMIT :limit OFFSET :offset")
    suspend fun getNewspapersSortedByDate(offset: Int, limit: Int): List<Newspaper>

    @Query("SELECT * FROM disks ORDER BY name LIMIT :limit OFFSET :offset")
    suspend fun getDisksSortedByName(offset: Int, limit: Int): List<Disk>

    @Query("SELECT * FROM disks ORDER BY added_date LIMIT :limit OFFSET :offset")
    suspend fun getDisksSortedByDate(offset: Int, limit: Int): List<Disk>


    @Query("""
        SELECT id, name, access, item_type AS itemType, added_date AS addedDate FROM books
        UNION SELECT id, name, access, item_type AS itemType, added_date AS addedDate FROM newspapers
        UNION SELECT id, name, access, item_type AS itemType, added_date AS addedDate FROM disks
        ORDER BY addedDate
    """)
    fun observeAllItems(): Flow<List<LibraryItemEntity>>




}

