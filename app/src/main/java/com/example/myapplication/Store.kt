package com.example.myapplication

interface Store <T : LibraryItem> {
    fun sellItem(): T
}