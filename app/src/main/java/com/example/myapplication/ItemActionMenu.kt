package com.example.myapplication

fun itemActionMenu(listOfItems: List<LibraryItem>) {
    val itemIndex: Int = userInputtedNumberOfItem(listOfItems)
    if (itemIndex == -1) return
    val item = listOfItems[itemIndex]
    val digitization = DigitizationCabinet()
    while (true) {
        println("1 - Взять домой")
        println("2 - Читать в читальном зале")
        println("3 - Показать подробную информацию")
        println("4 - Показать краткую информацию")
        println("5 - Вернуть")
        println("6 - Оцифровать")
        println("7 - Вернуться в главное меню")

        println("Выберете действие:")
        when (readlnOrNull()?.toIntOrNull()) {
            1 -> if (item is Rentandable) item.takeHome()
            else println("Газету нельзя брать домой")

            2 -> if (item is InLibraryUse) item.readInLibrary()
            else println("Диском нельзя пользоваться в читальном зале")

            3 -> println(item.getFullInfo())
            4 -> println(item.getShortInfo())
            5 -> item.returnItem()
            6 -> {
                if (item is InLibraryUse) {
                    try {
                        println(digitization.digitization(item).getFullInfo())
                    } catch (e: IllegalArgumentException) {
                        println(e.message)
                    }
                } else {
                    println("Диск уже оцифрован")
                }
            }

            7 -> return
            else -> println("Неверный выбор")
        }
    }
}