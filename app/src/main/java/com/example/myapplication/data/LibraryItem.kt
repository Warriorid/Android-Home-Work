package com.example.myapplication.data


interface LibraryItem {
    val id: Int?
    val name: String
    val access: Boolean
    val itemType: String
    val addedDate: Long
}