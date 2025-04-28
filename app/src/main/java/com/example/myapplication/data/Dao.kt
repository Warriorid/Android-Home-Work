package com.example.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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



}

