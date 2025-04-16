package com.example.myapplication.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication.R
import com.example.myapplication.consoleapp.Book
import com.example.myapplication.consoleapp.Disk
import com.example.myapplication.consoleapp.DiskType
import com.example.myapplication.data.DataRepository
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.consoleapp.Newspaper
import com.example.myapplication.data.ItemType
import com.example.myapplication.data.Month
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


    private fun toolbarMenu (binding: ActivityLibraryBinding, context: Context, addItemLauncher: ActivityResultLauncher<Intent>) {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_book -> {
                    addItemLauncher.launch(ItemActivityNavigator.createIntentType(context, ItemType.BOOK))
                    true
                }

                R.id.action_newspaper -> {
                    addItemLauncher.launch(
                        ItemActivityNavigator.createIntentType(context, ItemType.NEWSPAPER))
                    true
                }

                R.id.action_disk -> {
                    addItemLauncher.launch(ItemActivityNavigator.createIntentType(context, ItemType.DISK))
                    true
                }

                else -> false
            }
        }
    }
}