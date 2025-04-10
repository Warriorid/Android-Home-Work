package com.example.myapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication.data.DataRepository
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.databinding.ActivityLibraryBinding


class LibraryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLibraryBinding.inflate(layoutInflater) }
    private lateinit var libraryAdapter: LibraryAdapter
    private lateinit var viewModel: MainViewModel

    private val addItemLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            @Suppress("DEPRECATION")
            val newItem = result.data?.getParcelableExtra<LibraryItem>(ItemActivityNavigator.EXTRA_ITEM)
            newItem?.let { item ->
                viewModel.updateItems(listOf(item))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        libraryAdapter = LibraryAdapter()
        binding.recyclerView.adapter = libraryAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        toolbarMenu(binding, this, addItemLauncher)
        initViewModel()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.item.observe(this) { items ->
            libraryAdapter.submitList(items)
        }
        viewModel.updateItems(DataRepository.listOfAllTypes)
        val callback = LibraryItemTouchHelper(libraryAdapter, viewModel)
        ItemTouchHelper(callback).attachToRecyclerView(binding.recyclerView)
    }
}