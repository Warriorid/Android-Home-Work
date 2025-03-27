package com.example.myapplication

interface Digitization<in T : InLibraryUse> {
    fun toDisk(item: T): Disk
}