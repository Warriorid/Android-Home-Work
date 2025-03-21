package com.example.myapplication

class NewspaperStand():Store<Newspaper> {
    override fun sellItem(): Newspaper {
        return Newspaper(
            "Метро", 999, true, 2000, "Март"
        )
    }
}