package com.example.myapplication

class DiskStore():Store<Disk> {
    override fun sellItem(): Disk {
        return Disk(
            "Хиты 2000х", 55, true, "CD"
        )
    }
}