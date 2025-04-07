package com.example.myapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication.databinding.ActivityLibraryBinding


class LibraryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityLibraryBinding.inflate(layoutInflater) }
    private lateinit var adapter: LibraryAdapter
    private lateinit var viewModel: MainViewModel

    private val addItemLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra(
                    ItemActivityNavigator.EXTRA_ITEM,
                    LibraryItem::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                result.data?.getParcelableExtra(ItemActivityNavigator.EXTRA_ITEM)
            }
            newItem?.let { item ->
                viewModel.updateItems(listOf(item))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        adapter = LibraryAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_book -> {
                    addItemLauncher.launch(ItemActivityNavigator.createIntentForAdd(this, "Book"))
                    true
                }

                R.id.action_newspaper -> {
                    addItemLauncher.launch(
                        ItemActivityNavigator.createIntentForAdd(
                            this,
                            "Newspaper"
                        )
                    )
                    true
                }

                R.id.action_disk -> {
                    addItemLauncher.launch(ItemActivityNavigator.createIntentForAdd(this, "Disk"))
                    true
                }

                else -> false
            }
        }

        initViewModel()
    }

    private fun initViewModel() {
        val factory = ViewModelFactory()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        viewModel.item.observe(this) { items ->
            adapter.submitList(items)
        }
        viewModel.updateItems(DataRepository.listOfAllTypes)
        val callback = LibraryItemTouchHelper(adapter, viewModel)
        ItemTouchHelper(callback).attachToRecyclerView(binding.recyclerView)
    }
}