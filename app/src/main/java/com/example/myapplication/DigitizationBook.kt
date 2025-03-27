package com.example.myapplication

class DigitizationBook() : Digitization<Book> {
    override fun toDisk(item: Book): Disk {
        if (item.access) {
            println("Книга ${item.name} оцифровывается")
            return Disk(item.name, item.id, true, DiskType.CD.toString())
        }
        throw IllegalArgumentException("Книга недоступна")
    }

}