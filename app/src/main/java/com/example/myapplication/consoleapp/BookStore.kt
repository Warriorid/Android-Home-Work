package com.example.myapplication.consoleapp

class BookStore() : Store<Book> {
    override fun sell(): Book {
        return Book(
            "Сборник стихов", 33, true, 150, "А. C. Пушкин"
        )
    }
}