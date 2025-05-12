package com.example.myapplication.domain.model


interface LibraryItem {
    val id: Int?
    val name: String
    val access: Boolean
    val itemType: String
    val addedDate: Long
}