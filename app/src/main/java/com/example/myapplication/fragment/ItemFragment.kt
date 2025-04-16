package com.example.myapplication.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.activity.DisplayAddItem
import com.example.myapplication.activity.DisplayItems
import com.example.myapplication.activity.ItemFragmentNavigator
import com.example.myapplication.activity.MainViewModel
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

        Log.d("item", "!!!onSaveInstanceState ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.closeFragment.value == true){
            savedInstanceState?.clear()
        }
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        itemType = savedInstanceState?.getString(ItemFragmentNavigator.EXTRA_TYPE)
            ?: viewModel.getItemType()
        Log.d("item", "!!!onCreate ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        Log.d("item", "!!!onCreateView ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("item", "!!!onViewCreated ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
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
            newItem.let {  item ->
                viewModel.updateItems(item!!)
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
        viewModel.closeFragment(true)
        parentFragmentManager.beginTransaction()
            .remove(this@ItemFragment)
            .commit()
        parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onStart() {
        super.onStart()
        Log.d("item", "!!!onStart ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("item", "!!!onResume ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("item", "!!!onStop ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("item", "!!!onDestroyView( ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("item", "!!!onDestroy ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("item", "!!!onDetach ${viewModel.getItemType()}, ${viewModel.selectedItem.value}")
    }

}