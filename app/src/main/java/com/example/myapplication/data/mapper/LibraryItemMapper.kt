package com.example.myapplication.data.mapper

import com.example.myapplication.data.model.LibraryItemEntity

class LibraryItemMapper {
    companion object {
        fun toDomain(entity: LibraryItemEntity): com.example.myapplication.domain.model.LibraryItem {
            return object : com.example.myapplication.domain.model.LibraryItem {
                override val id: Int? = entity.id.toIntOrNull()
                override val name: String = entity.name
                override val access: Boolean = entity.access
                override val itemType: String = entity.itemType
                override val addedDate: Long = entity.addedDate
            }
        }
    }
}