package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.domain.model.Month

@Entity (tableName = "newspapers")
data class Newspaper (
    @PrimaryKey(autoGenerate = true)
    override var id: Int? = null,
    @ColumnInfo(name = "name")
    override var name: String,
    @ColumnInfo(name = "access")
    override var access: Boolean,
    @ColumnInfo(name = "item_type")
    override val itemType: String = "newspaper",
    @ColumnInfo(name = "added_date")
    override val addedDate: Long,
    @ColumnInfo(name = "number")
    var number: Int,
    @ColumnInfo(name = "month")
    var month: Month
    ): LibraryItem