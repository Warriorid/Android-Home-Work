package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "books")
data class Book (
    @PrimaryKey(autoGenerate = true)
    override var id: Int? = null,
    @ColumnInfo(name = "name")
    override var name: String,
    @ColumnInfo(name = "access")
    override var access: Boolean,
    @ColumnInfo(name = "item_type")
    override val itemType: String = "book",
    @ColumnInfo(name = "added_date")
    override val addedDate: Long,
    @ColumnInfo(name = "author")
    var author: String,
    @ColumnInfo(name = "pages")
    var pages: Int
): LibraryItem
