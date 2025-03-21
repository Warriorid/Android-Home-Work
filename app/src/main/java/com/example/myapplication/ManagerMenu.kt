package com.example.myapplication

fun managerMenu(manager: Manager){
    while (true){
        println("Выберете действие, которое должен выполнить менеджер:")
        println("1 - Купить книгу")
        println("2 - Купить газету")
        println("3 - Купить диск")
        println("4 - Вернуться в главное меню")
        val inputtedValueByUser = readlnOrNull()?.toIntOrNull()
        when (inputtedValueByUser){
            1 -> {
                val store = BookStore()
                manager.buyItemForLibrary(store)
            }
            2 -> {
                val store = NewspaperStand()
                manager.buyItemForLibrary(store)
            }
            3 -> {
                val store = DiskStore()
                manager.buyItemForLibrary(store)
            }
            4 -> return
            else -> {println("Неверный выбор"); continue}
        }
    }
}