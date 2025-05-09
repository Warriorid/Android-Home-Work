package com.example.myapplication.consoleapp


import kotlinx.parcelize.Parcelize

@Parcelize
data class Disk(
    override val name: String,
    override val id: Int,
    override var access: Boolean,
    val type: String

) : LibraryItem(), Rentandable {

    override fun getFullInfo() = """
        $type $name доступен: ${if (access) "да" else "нет"}
    """.trimIndent()

    override fun getShortInfo(): String = """
        $name доступна: ${if (access) "да" else "нет"}
    """.trimIndent()

    override fun returnItem() {
        if (access) {
            println("Невозможно выполнить данное действие, диск уже в библиотеке")
            return
        }
        println("Диск $name вернули в библиотеку")
        access = true
    }

    override fun takeHome() {
        if (access) {
            println("Диск $name взяли домой")
            access = false
        } else {
            println("Невозможно выполнить данное действие, диск забрали домой")
        }
    }
}