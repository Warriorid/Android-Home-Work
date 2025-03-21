package com.example.myapplication

class BookStore(): Store<Book> {
    override fun sellItem(): Book {
        return Book(
            "Сборник стихов", 33, true, 150, "А. C. Пушкин"
        )
    }
}