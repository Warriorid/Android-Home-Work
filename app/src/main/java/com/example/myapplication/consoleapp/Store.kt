package com.example.myapplication.consoleapp

interface Store<out T : LibraryItem> {
    fun sell(): T
}