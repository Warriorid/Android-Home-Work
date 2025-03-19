package com.example.myapplication

fun main(){
    val listOfBooks = listOf(
        Book("Война и мир", 1000, "Л. Н. Толстой", 1, true),
        Book("Капитанская дочка", 250, "А. C. Пушкин", 2, true),
        Book("Преступление и наказание", 430, "Ф. М. Достоевский", 3, false),
        Book("Мастер и Маргарита", 480, "М. А. Булгаков", 4, true),
        Book("Анна Каренина", 864, "Л. Н. Толстой", 5, true),
        Book("Евгений Онегин", 320, "А. С. Пушкин", 6, true)
    )
    val listOfNewspapers = listOf(
        Newspaper(794, "Сельская жизнь", 17245, true),
        Newspaper(501, "Комсомольская правда", 99, true),
        Newspaper(302, "Московский комсомолец", 120, false),
        Newspaper(456, "Аргументы и факты", 200, true),
        Newspaper(789, "Известия", 150, true),
        Newspaper(123, "Российская газета", 300, true)
    )

    val listOfDisks = listOf(
        Disk("DVD", "Дэдпул и Росомаха", 52, true),
        Disk("DVD", "Терминатор", 348, false),
        Disk("DVD", "Интерстеллар", 169, true),
        Disk("CD", "Начало", 148, true),
        Disk("DVD", "Матрица", 136, true),
        Disk("CD", "Гладиатор", 155, true)
    )

    mainMenu(listOfBooks, listOfNewspapers, listOfDisks)
}