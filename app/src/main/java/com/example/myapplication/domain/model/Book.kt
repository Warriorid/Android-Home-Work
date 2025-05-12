package com.example.myapplication.domain.model

data class Book(
    override val id: Int? = null,
    override val name: String,
    override val access: Boolean,
    override val itemType: String = "book",
    override val addedDate: Long,
    val author: String,
    val pages: Int
): LibraryItem