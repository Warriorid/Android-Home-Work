package com.example.myapplication

import android.os.Parcelable

abstract class LibraryItem : Returnable, Parcelable {
    abstract val name: String
    abstract val id: Int
    abstract var access: Boolean
    abstract fun getFullInfo(): String
    abstract fun getShortInfo(): String
}