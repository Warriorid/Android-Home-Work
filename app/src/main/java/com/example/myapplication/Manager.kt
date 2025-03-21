package com.example.myapplication

class Manager {
    fun <T: LibraryItem> buyItemForLibrary (store: Store<T>): T{
        println("Сделка совершена, информация о купленном товаре: ${store.sellItem().getFullInfo()}")
        return store.sellItem()
    }
}