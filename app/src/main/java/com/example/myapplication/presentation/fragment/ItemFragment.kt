package com.example.myapplication.presentation.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.common.navigation.ItemFragmentNavigator
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.domain.model.LibraryItem
import com.example.myapplication.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private var itemType: String? = null
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    private var isLandscape: Boolean = false
    private var closeFragment = false

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (viewModel.selectedItem.value == null) {
            outState.putString(ItemFragmentNavigator.EXTRA_TYPE, itemType)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        itemType = savedInstanceState?.getString(ItemFragmentNavigator.EXTRA_TYPE)
            ?: viewModel.getItemType()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedItem.observe(viewLifecycleOwner) { item ->
            item?.let {
                DisplayItems(binding).displayItem(it)
            }
        }

        if (viewModel.selectedItem.value == null) {
            viewModel.getItemType()?.let { type ->
                viewModel.setItemType(type)
                DisplayAddItem(binding).display(type)

            }
        }

        binding.saveButtom.setOnClickListener {
            val newItem = DisplayAddItem(binding).createItem(itemType!!)
            if (newItem != null) {
                viewModel.updateItems(newItem as LibraryItem)
                viewModel.addItem(newItem)
                closeFragment()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            closeFragment()
        }
    }

    private fun closeFragment() {
        closeFragment = true
        viewModel.clearSelectedItem()
        viewModel.setItemType(null)
        parentFragmentManager.beginTransaction()
            .remove(this@ItemFragment)
            .commit()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}