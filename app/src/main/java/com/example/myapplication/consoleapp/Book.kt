package com.example.myapplication.consoleapp

import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    override val name: String,
    override val id: Int,
    override var access: Boolean,
    val count: Int,
    val author: String

) : LibraryItem(), InLibraryUse, Rentandable {

    override fun getFullInfo() = """
        книга $name ($count стр.) автора: $author c id: $id доступна: ${if (access) "да" else "нет"}
    """.trimIndent()

    override fun getShortInfo() = """
        $name доступна: ${if (access) "да" else "нет"}
    """.trimIndent()

    override fun returnItem() {
        if (access) {
            println("Невозможно выполнить данное действие, книга уже в библиотеке")
            return
        }
        println("Книгу $name вернули в библиотеку")
        access = true
    }

    override fun readInLibrary() {
        if (access) {
            println("Книгу $id взяли в читальный зал")
            access = false
            return
        } else println("Невозможно выполнить данное действие, книгу забрали")
        return
    }

    override fun takeHome() {
        if (access) {
            println("Книгу $name взяли домой")
            access = false
        } else {
            println("Невозможно выполнить данное действие, книгу забрали")
        }
    }
}