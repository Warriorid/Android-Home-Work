package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import com.example.myapplication.consoleapp.LibraryItem

object ItemActivityNavigator {

    const val EXTRA_ITEM = "extra_item"
    const val EXTRA_TYPE = "extra_type"

    fun createIntent(context: Context, item: LibraryItem): Intent {
        return Intent(context, ItemActivity::class.java).apply {
            putExtra(EXTRA_ITEM, item)
        }
    }

    fun createIntentForAdd(context: Context, itemType: String): Intent {
        return Intent(context, ItemActivity::class.java).apply {
            putExtra(EXTRA_TYPE, itemType)
        }
    }
}