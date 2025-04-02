package com.example.myapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityLibraryBinding


class Library : AppCompatActivity() {

    private val binding by lazy {
        ActivityLibraryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.recyclerView.apply {
            adapter = LibraryAdapter(DataRepository.listOfAllTypes)
            layoutManager = LinearLayoutManager(this@Library)
        }

    }
}