package com.example.myapplication.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.domain.model.LibraryItem

@Entity (tableName = "disks")
data class Disk (
    @PrimaryKey(autoGenerate = true)
    override var id: Int? = null,
    @ColumnInfo(name = "name")
    override var name: String,
    @ColumnInfo(name = "access")
    override var access: Boolean,
    @ColumnInfo(name = "item_type")
    override val itemType: String = "disk",
    @ColumnInfo(name = "added_date")
    override val addedDate: Long,
    @ColumnInfo(name = "type")
    var diskType: String
): LibraryItem