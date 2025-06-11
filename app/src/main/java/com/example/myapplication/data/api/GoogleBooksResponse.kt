package com.example.myapplication.data.api

import com.google.gson.annotations.SerializedName


data class GoogleBooksResponse(
    @SerializedName("items") val items: List<GoogleBookItem>?
)

data class GoogleBookItem(
    @SerializedName("id") val id: String?,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo?
)

data class VolumeInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("pageCount") val pageCount: Int?
)