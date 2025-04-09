package com.example.myapplication.consoleapp

interface Digitization<in T : InLibraryUse> {
    fun toDisk(item: T): Disk
}