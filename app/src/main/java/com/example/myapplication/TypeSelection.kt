package com.example.myapplication

inline fun <reified T> typeSelection(item: List<LibraryItem>): List<T> {
    return item.filterIsInstance<T>()
}