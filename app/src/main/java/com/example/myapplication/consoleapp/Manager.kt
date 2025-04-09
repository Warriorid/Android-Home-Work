package com.example.myapplication.consoleapp

class Manager {
    fun <T : LibraryItem> buy(store: Store<T>): T {
        println("Сделка совершена, информация о купленном товаре: ${store.sell().getFullInfo()}")
        return store.sell()
    }
}