package com.example.myapplication.consoleapp

import com.example.myapplication.data.Month

class NewspaperStore() : Store<Newspaper> {
    override fun sell(): Newspaper {
        return Newspaper(
            "Метро", 999, true, 2000, Month.Март
        )
    }
}