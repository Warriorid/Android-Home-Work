package com.example.myapplication.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.activity.DisplayAddItem
import com.example.myapplication.activity.DisplayItems
import com.example.myapplication.activity.ItemActivityNavigator
import com.example.myapplication.activity.MainViewModel
import com.example.myapplication.consoleapp.Book
import com.example.myapplication.consoleapp.Disk
import com.example.myapplication.consoleapp.Newspaper
import com.example.myapplication.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private lateinit var binding: FragmentItemBinding
    private var itemType: String? = null
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    private var isLandscape: Boolean = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(ItemActivityNavigator.EXTRA_TYPE, itemType)
        outState.putBoolean(ItemActivityNavigator.EXTRA_CONDITION, isLandscape)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemType = savedInstanceState?.getString(ItemActivityNavigator.EXTRA_TYPE)
            ?: arguments?.getString(ItemActivityNavigator.EXTRA_TYPE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLandscape = (savedInstanceState?.getBoolean(ItemActivityNavigator.EXTRA_CONDITION)
            ?: resources.configuration.orientation) == Configuration.ORIENTATION_LANDSCAPE
        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                DisplayItems(binding).displayItem(it)
                itemType = when(it) {
                    is Book -> "Book"
                    is Newspaper -> "Newspaper"
                    is Disk -> "Disk"
                    else -> null
                }
            }
        }

        if (viewModel.selectedItem.value == null) {
            itemType?.let { type ->
                viewModel.setItemType(type)
                DisplayAddItem(binding).display(type)

            }
        }

        binding.saveButtom.setOnClickListener {
            val newItem = DisplayAddItem(binding).createItem(itemType!!)
            if (newItem != null) {
                viewModel.updateItems(newItem)
                if (isLandscape) {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .remove(this)
                        .commit()
                } else {
                    parentFragmentManager.popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment()
        }
    }
    private fun closeFragment() {
        if (isLandscape) {
            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        } else {
            parentFragmentManager.popBackStack()
        }
        viewModel.clearSelectedItem()
    }

}