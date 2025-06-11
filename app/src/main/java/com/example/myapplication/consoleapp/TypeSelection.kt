package com.example.myapplication.consoleapp

inline fun <reified T> typeSelection(item: List<LibraryItem>): List<T> {
    return item.filterIsInstance<T>()
}