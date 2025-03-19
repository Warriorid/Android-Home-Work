package com.example.myapplication

fun userInputtedNumberOfItem(listOfItems: List<LibraryItem>): Int{
    while (true) {
        listOfItems.forEachIndexed { index, item ->
            println("${index + 1}) ${item.getShortInfo()}")
        }
        println("Введите порядковый номер желаемого объекта или 0 для отмены:")
        val itemIndex = readlnOrNull()?.toIntOrNull()?.minus(1)
        when {
            itemIndex == -1 -> return -1
            itemIndex != null && itemIndex in listOfItems.indices -> return itemIndex
            else -> println("Неверный выбор")
        }
    }
}