package com.example.myapplication

fun main(){
    val listOfAllTypes = listOf(Book("Война и мир", 1, true, 1000, "Л. Н. Толстой"),
        Book("Капитанская дочка", 2, true, 250, "А. С. Пушкин"),
        Book("Преступление и наказание", 3, false, 430, "Ф. М. Достоевский"),
        Book("Мастер и Маргарита", 4, true, 480, "М. А. Булгаков"),
        Newspaper("Сельская жизнь", 794, true, 17245, "Январь"),
        Newspaper("Комсомольская правда", 99, true, 501, "Февраль"),
        Newspaper("Московский комсомолец", 120, false, 302, "Март"),
        Disk("Дэдпул и Росомаха", 52, true, "DVD"),
        Disk("Терминатор", 348, false, "DVD"),
        Disk("Интерстеллар", 169, true, "DVD"),
        Disk("Начало", 148, true, "CD"),
        Disk("Матрица", 136, true, "DVD"),
        Disk("Гладиатор", 155, true, "CD"),
        Newspaper("Аргументы и факты", 200, true, 456, "Июнь"),
        Newspaper("Известия", 150, true, 789, "Август"),
        Newspaper("Российская газета", 300, true, 123, "Декабрь"),
        Book("Анна Каренина", 5, true, 864, "Л. Н. Толстой"),
        Book("Евгений Онегин", 6, true, 320, "А. С. Пушкин")
    )

    mainMenu(listOfAllTypes)
}