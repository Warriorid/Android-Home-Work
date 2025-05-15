package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LibraryItemEntity(
    @PrimaryKey val id: String,
    val name: String,
    val access: Boolean,
    val itemType: String,
    val addedDate: Long
)