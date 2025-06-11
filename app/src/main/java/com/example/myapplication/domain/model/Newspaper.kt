package com.example.myapplication.domain.model

data class Newspaper (
    override var id: Int? = null,
    override var name: String,
    override var access: Boolean,
    override val itemType: String = "newspaper",
    override val addedDate: Long,
    var number: Int,
    var month: Month
): LibraryItem