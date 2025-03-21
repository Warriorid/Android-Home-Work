package com.example.myapplication

class DigitizationCabinet {
        fun <T : LibraryItem> digitization(item: T): Disk {
        when (item) {
            is Book -> {
                if (item.access){
                    println("Книга ${item.name} оцифровывается")
                    return Disk(item.name, item.id, true, DiskType.CD.toString())
                }
                throw IllegalArgumentException("Книга недоступна")
            }
            is Newspaper -> {
                if (item.access){
                    println("Газета ${item.name} оцифровывается")
                    return Disk(item.name, item.id, true, DiskType.CD.toString())
                }
                throw IllegalArgumentException("Газета недоступна")
            }
            else -> throw IllegalArgumentException("Диск уже оцифрован")
        }
    }
}