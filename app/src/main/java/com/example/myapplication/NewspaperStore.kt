package com.example.myapplication

class NewspaperStore() : Store<Newspaper> {
    override fun sell(): Newspaper {
        return Newspaper(
            "Метро", 999, true, 2000, Month.Март
        )
    }
}