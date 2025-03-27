package com.example.myapplication

interface Store<out T : LibraryItem> {
    fun sell(): T
}