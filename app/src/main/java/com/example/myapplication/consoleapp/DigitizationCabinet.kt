package com.example.myapplication.consoleapp

class DigitizationCabinet {
    fun <T : InLibraryUse> digitization(item: T): Disk {
        return when (item) {
            is Book -> DigitizationBook().toDisk(item)

            is Newspaper -> DigitizationNewspaper().toDisk(item)

            else -> throw IllegalArgumentException("Диск уже оцифрован")
        }
    }
}