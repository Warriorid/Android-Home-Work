package com.example.myapplication

abstract class LibraryItem : Returnable {
    abstract val name: String
    abstract val id: Int
    abstract var access: Boolean
    abstract fun getFullInfo(): String
    abstract fun getShortInfo(): String
}