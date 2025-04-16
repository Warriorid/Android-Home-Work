package com.example.myapplication.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.databinding.FragmentItemBinding

class ItemActivity : AppCompatActivity() {

    private val binding by lazy {
        FragmentItemBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val item = intent.getParcelableExtra(ItemFragmentNavigator.EXTRA_ITEM, LibraryItem::class.java)
        val itemType = intent.getStringExtra(ItemFragmentNavigator.EXTRA_TYPE).toString()
        if (item != null) {
            DisplayItems(binding).displayItem(item)
        } else {
            val display = DisplayAddItem(binding)
            display.display(itemType)

            binding.saveButtom.setOnClickListener {
                val newItem = display.createItem(itemType)
                if (newItem != null) {
                    val resultIntent = Intent()
                    resultIntent.putExtra(ItemFragmentNavigator.EXTRA_ITEM, newItem)
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }
}