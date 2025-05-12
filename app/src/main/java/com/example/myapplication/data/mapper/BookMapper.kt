package com.example.myapplication.data.mapper


object BookMapper {
    fun toData(domain: com.example.myapplication.domain.model.Book): com.example.myapplication.data.model.Book {
        return com.example.myapplication.data.model.Book(
            id = domain.id,
            name = domain.name,
            access = domain.access,
            itemType = domain.itemType,
            addedDate = domain.addedDate,
            author = domain.author,
            pages = domain.pages
        )
    }

    fun toDomain(data: com.example.myapplication.data.model.Book): com.example.myapplication.domain.model.Book {
        return com.example.myapplication.domain.model.Book(
            id = data.id,
            name = data.name,
            access = data.access,
            itemType = data.itemType,
            addedDate = data.addedDate,
            author = data.author,
            pages = data.pages
        )
    }
}