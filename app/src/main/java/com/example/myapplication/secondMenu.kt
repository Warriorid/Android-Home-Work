package com.example.myapplication


fun <T: libObj>secondMenu(listOfObj: List<T>){
    val objIndex: Int = objNumber(listOfObj)
    if (objIndex == -1){return}
    while (true){
        println("1 - Взять домой")
        println("2 - Читать в читальном зале")
        println("3 - Показать подробную информацию")
        println("4 - Вернуть")
        println("5 - Вернуться в главное меню")


        println("Выберете действие:")
        when(readlnOrNull()?.toIntOrNull()){
            1 -> bringHome(listOfObj, objIndex)
            2 -> readingRoom(listOfObj, objIndex)
            3 -> fullInformation(listOfObj, objIndex)
            4 -> returnObj(listOfObj, objIndex)
            5 -> return
            else -> println("Неверный выбор")
        }
    }
}