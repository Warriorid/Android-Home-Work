package com.example.myapplication.data.mapper

object DiskMapper {
    fun toData(domain: com.example.myapplication.domain.model.Disk): com.example.myapplication.data.model.Disk {
        return com.example.myapplication.data.model.Disk(
            id = domain.id,
            name = domain.name,
            access = domain.access,
            itemType = domain.itemType,
            addedDate = domain.addedDate,
            diskType = domain.diskType
        )
    }

    fun toDomain(data: com.example.myapplication.data.model.Disk): com.example.myapplication.domain.model.Disk {
        return com.example.myapplication.domain.model.Disk(
            id = data.id,
            name = data.name,
            access = data.access,
            itemType = data.itemType,
            addedDate = data.addedDate,
            diskType = data.diskType
        )
    }
}