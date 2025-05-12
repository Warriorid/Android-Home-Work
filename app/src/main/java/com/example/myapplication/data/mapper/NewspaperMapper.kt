package com.example.myapplication.data.mapper

object NewspaperMapper {
    fun toData(domain: com.example.myapplication.domain.model.Newspaper): com.example.myapplication.data.model.Newspaper {
        return com.example.myapplication.data.model.Newspaper(
            id = domain.id,
            name = domain.name,
            access = domain.access,
            itemType = domain.itemType,
            addedDate = domain.addedDate,
            number = domain.number,
            month = domain.month
        )
    }

    fun toDomain(data: com.example.myapplication.data.model.Newspaper): com.example.myapplication.domain.model.Newspaper {
        return com.example.myapplication.domain.model.Newspaper(
            id = data.id,
            name = data.name,
            access = data.access,
            itemType = data.itemType,
            addedDate = data.addedDate,
            number = data.number,
            month = data.month
        )
    }
}