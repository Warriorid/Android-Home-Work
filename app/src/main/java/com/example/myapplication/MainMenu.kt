package com.example.myapplication

fun mainMenu(listOfBooks: List<Book>, listOfNewspapers: List<Newspaper>, listOfDisks: List<Disk>){
    while (true){
        val manager1 = Manager()
        println("Главное меню")
        println("1 - Показать книги")
        println("2 - Показать газеты")
        println("3 - Показать диски")
        println("4 - Озадачить менеджера")
        println("5 - Выход из программы")

        when(readlnOrNull()?.toIntOrNull()){
            1 -> itemActionMenu(listOfBooks)
            2 -> itemActionMenu(listOfNewspapers)
            3 -> itemActionMenu(listOfDisks)
            4 -> managerMenu(manager1)
            5 -> return
            else -> println("Неверный выбор")
        }
    }
}