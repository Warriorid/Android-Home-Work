package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication.databinding.ActivityLibraryBinding


class LibraryActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLibraryBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.recyclerView.apply {
            val adapter = LibraryAdapter(DataRepository.listOfAllTypes)
            this.adapter = adapter
            layoutManager = LinearLayoutManager(this@LibraryActivity)
            val callback = LibraryItemTouchHelper(adapter)
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(this)
        }

    }
}