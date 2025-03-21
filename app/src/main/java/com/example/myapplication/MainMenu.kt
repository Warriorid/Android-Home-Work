package com.example.myapplication

fun mainMenu(listOfItem: List<LibraryItem>){
    while (true){
        val manager1 = Manager()
        println("Главное меню")
        println("1 - Показать книги")
        println("2 - Показать газеты")
        println("3 - Показать диски")
        println("4 - Озадачить менеджера")
        println("5 - Выход из программы")

        when(readlnOrNull()?.toIntOrNull()){
            1 -> itemActionMenu(typeSelection<Book>(listOfItem))
            2 -> itemActionMenu(typeSelection<Newspaper>(listOfItem))
            3 -> itemActionMenu(typeSelection<Disk>(listOfItem))
            4 -> managerMenu(manager1)
            5 -> return
            else -> println("Неверный выбор")
        }
    }
}