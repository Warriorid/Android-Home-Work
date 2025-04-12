package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.activity.DisplayAddItem
import com.example.myapplication.activity.DisplayItems
import com.example.myapplication.activity.ItemActivityNavigator
import com.example.myapplication.activity.MainViewModel
import com.example.myapplication.databinding.FragmentItemBinding

class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!
    private var itemType: String? = null
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemType = arguments?.getString(ItemActivityNavigator.EXTRA_TYPE)
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
                DisplayItems(binding).displayItem(item)
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
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}