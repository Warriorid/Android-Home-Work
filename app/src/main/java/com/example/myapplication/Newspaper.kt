package com.example.myapplication

data class Newspaper(
    private val number: Int,
    override val name: String,
    override val id: Int,
    override var access: Boolean

): LibraryItem(), InLibraryUse {

    override fun getFullInfo() = """
        выпуск: $number газеты $name с id: $id доступен: ${if (access) "да" else "нет"}
    """.trimIndent()

    override fun getShortInfo(): String = """
         $name доступна: ${if (access) "да" else "нет"}
    """.trimIndent()

    override fun returnItem() {
        if (access) {
            println("Невозможно выполнить данное действие, газета уже в библиотеке")
            return
        }
        println("Газету $name вернули в библиотеку")
        access = true
    }

    override fun readInLibrary() {
        if (access){
            println("Газету $id взяли в читальный зал")
            access = false
            return
        }
        else println("Невозможно выполнить данное действие, газета в читальном зале")
        return
    }
}