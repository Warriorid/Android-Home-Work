package com.example.myapplication

fun mainMenu(listOfBooks: List<Book>, listOfNewspapers: List<Newspaper>, listOfDisks: List<Disk>){

    while (true){
        println("Главное меню")
        println("1 - Показать книги")
        println("2 - Показать газеты")
        println("3 - Показать диски")
        println("4 - Выход из программы")

        when(readlnOrNull()?.toIntOrNull()){
            1 -> secondMenu(listOfBooks)
            2 -> secondMenu(listOfNewspapers)
            3 -> secondMenu(listOfDisks)
            4 -> return
            else -> println("Неверный выбор")
        }
    }
}