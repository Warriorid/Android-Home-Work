package com.example.myapplication.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.model.Book
import com.example.myapplication.data.model.Disk
import com.example.myapplication.data.model.Newspaper

@Database (entities = [Book::class, Newspaper::class, Disk::class], version = 1)
abstract class MainDB: RoomDatabase() {
    abstract fun getDb(): Dao
    companion object{
        fun getDB(context: Context) : MainDB {
            return Room.databaseBuilder(
                context.applicationContext, MainDB::class.java, "library.db"
            ).build()
        }
    }
}