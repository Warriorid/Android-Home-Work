package com.example.myapplication.domain.model


data class Disk (
    override var id: Int? = null,
    override var name: String,
    override var access: Boolean,
    override val itemType: String = "disk",
    override val addedDate: Long,
    var diskType: String
): LibraryItem