package com.example.myapplication

class Book (
    override val name: String,
    private val count: Int,
    private val author: String,
    override val id: Int,
    override var access: Boolean
): libObj{
    fun fullInfo() = """
        книга $name ($count стр.) автора: $author c id: $id доступна: ${if (access) "да" else "нет"}
    """.trimIndent()
}

class Newspaper(
    private val number: Int,
    override val name: String,
    override val id: Int,
    override var access: Boolean
): libObj{
    fun fullInfo() = """
        выпуск: $number газеты $name с id: $id доступен: ${if (access) "да" else "нет"}
    """.trimIndent()
}


class Disk(
    private val type: String,
    override val name: String,
    override val id: Int,
    override var access: Boolean
): libObj{
    fun fullInfo() = """
        $type $name доступен: ${if (access) "да" else "нет"}
    """.trimIndent()
}



