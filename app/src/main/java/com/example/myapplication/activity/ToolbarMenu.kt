package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityLibraryBinding


fun toolbarMenu (binding: ActivityLibraryBinding, context: Context, addItemLauncher: ActivityResultLauncher<Intent>) {
    binding.toolbar.setOnMenuItemClickListener { menuItem ->
        when (menuItem.itemId) {
            R.id.action_book -> {
                addItemLauncher.launch(ItemActivityNavigator.createIntentForAdd(context, "Book"))
                true
            }

            R.id.action_newspaper -> {
                addItemLauncher.launch(
                    ItemActivityNavigator.createIntentForAdd(context, "Newspaper")
                )
                true
            }

            R.id.action_disk -> {
                addItemLauncher.launch(ItemActivityNavigator.createIntentForAdd(context, "Disk"))
                true
            }

            else -> false
        }
    }
}