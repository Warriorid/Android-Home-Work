package com.example.myapplication.consoleapp

class DigitizationNewspaper() : Digitization<Newspaper> {
    override fun toDisk(item: Newspaper): Disk {
        if (item.access) {
            println("Газета ${item.name} оцифровывается")
            return Disk(item.name, item.id, true, DiskType.CD.toString())
        }
        throw IllegalArgumentException("Газета недоступна")
    }
}