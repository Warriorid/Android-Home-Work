package com.example.myapplication

fun libObj.brefFormat() = """
     $name доступна: ${if (access) "да" else "нет"}
""".trimIndent()

fun <T: libObj> objNumber(listOfObj: List<T>): Int{
    while (true) {
        listOfObj.forEachIndexed { index, obj ->
            println("${index + 1}) ${obj.brefFormat()}")
        }
        println("Введите порядковый номер желаемого объекта или 0 для отмены:")
        val objIndex = readlnOrNull()?.toIntOrNull()?.minus(1)
        if (objIndex != null && objIndex in listOfObj.indices || objIndex == -1){
            return objIndex
        }
        else{
            println("Неверный выбор")
            continue
        }
    }
}

fun <T: libObj> bringHome(listOfObj: List<T>, index: Int){
    var type = ""
    if (listOfObj[index].access && (listOfObj[index] is Book || listOfObj[index] is Disk)) {
        listOfObj[index].access = false
        when (listOfObj[index]){
            is Book -> {type = "книгу"}
            is Disk -> {type = "диск"}
        }
        println("$type ${listOfObj[index].id} взяли домой")
        return
    } else println("Невозможно выполнить данное действие")
    return
}

fun <T: libObj> readingRoom(listOfObj: List<T>, index: Int){
    var type = ""
    if (listOfObj[index].access && (listOfObj[index] is Book || listOfObj[index] is Newspaper)){
        when (listOfObj[index]){
            is Book -> type = "книгу"
            is Newspaper -> type = "газету"
        }
        println("$type ${listOfObj[index].id} взяли в читальный зал")
        listOfObj[index].access = false
        return
    }
    else println("Невозможно выполнить данное действие")
    return
}

fun <T: libObj> returnObj(listOfObj: List<T>, index: Int){
    if (listOfObj[index].access) {
        println("Невозможно выполнить данное действие")
        return
    }
    val obj = listOfObj[index]
    val objType = when (obj) {
        is Book -> "Книгу"
        is Newspaper -> "Газету"
        else -> "Диск"
    }
    println("$objType ${obj.name} вернули в библиотеку")
    listOfObj[index].access = true
}

fun <T: libObj> fullInformation(listObj: List<T>, index: Int){
    when (val obj = listObj[index]){
        is Book -> println(obj.fullInfo())
        is Newspaper -> println(obj.fullInfo())
        is Disk -> println(obj.fullInfo())
        else -> println("объект не того типа")
    }
}