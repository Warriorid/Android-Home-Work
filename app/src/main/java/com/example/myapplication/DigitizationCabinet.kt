package com.example.myapplication

class DigitizationCabinet {
    fun <T : InLibraryUse> digitization(item: T): Disk {
        return when (item) {
            is Book -> {
                require(item.access) { "Книга недоступна" }
                println("Книга ${item.name} оцифровывается")
                Disk(item.name, item.id, true, DiskType.CD.toString())
            }

            is Newspaper -> {
                require(item.access) { "Газета недоступна" }
                println("Газета ${item.name} оцифровывается")
                Disk(item.name, item.id, true, DiskType.CD.toString())
            }

            else -> throw IllegalArgumentException("Диск уже оцифрован")
        }
    }
}