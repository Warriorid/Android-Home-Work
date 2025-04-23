package com.example.myapplication.consoleapp

fun managerMenu(manager: Manager) {
    while (true) {
        println("Выберете действие, которое должен выполнить менеджер:")
        println("1 - Купить книгу")
        println("2 - Купить газету")
        println("3 - Купить диск")
        println("4 - Вернуться в главное меню")
        val inputtedValueByUser = readlnOrNull()?.toIntOrNull()
        when (inputtedValueByUser) {
            1 -> {
                val store = BookStore()
                manager.buy(store)
            }

            2 -> {
                val store = NewspaperStore()
                manager.buy(store)
            }

            3 -> {
                val store = DiskStore()
                manager.buy(store)
            }

            4 -> return
            else -> {
                println("Неверный выбор"); continue
            }
        }
    }
}