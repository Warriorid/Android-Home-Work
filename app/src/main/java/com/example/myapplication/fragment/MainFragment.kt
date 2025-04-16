package com.example.myapplication.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.activity.ItemFragmentNavigator
import com.example.myapplication.activity.LibraryAdapter
import com.example.myapplication.activity.LibraryItemTouchHelper
import com.example.myapplication.activity.MainViewModel
import com.example.myapplication.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentAdapter: LibraryAdapter
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    private var isLandscape: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        toolbarMenu(binding, viewModel, parentFragmentManager, isLandscape)
        if (isLandscape && savedInstanceState == null && viewModel.selectedItem.value != null) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.itemFragment, ItemFragment())
                .commit()
        }

        fragmentAdapter = LibraryAdapter { item ->
            viewModel.selectItem(item)

            if (isLandscape) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.itemFragment, ItemFragment())
                    .commit()
            } else {
                parentFragmentManager.beginTransaction()
                    .add(R.id.mainFragment, ItemFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
        binding.recyclerView.apply {
            adapter = fragmentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val swipeDeleted = LibraryItemTouchHelper(fragmentAdapter, viewModel)
            ItemTouchHelper(swipeDeleted).attachToRecyclerView(this)
        }
        viewModel.item.observe(viewLifecycleOwner) { items ->
            fragmentAdapter.submitList(items)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun toolbarMenu(
        binding: FragmentMainBinding,
        viewModel: MainViewModel,
        fragmentManager: FragmentManager,
        isLandscape: Boolean
    ) {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            viewModel.clearSelectedItem()
            when (menuItem.itemId) {
                R.id.action_book -> {
                    viewModel.setItemType("Book")
                    openItemFragment(fragmentManager, "Book", isLandscape)
                    true
                }

                R.id.action_newspaper -> {
                    viewModel.setItemType("Newspaper")
                    openItemFragment(fragmentManager, "Newspaper", isLandscape)
                    true
                }

                R.id.action_disk -> {
                    viewModel.setItemType("Disk")
                    openItemFragment(fragmentManager, "Disk", isLandscape)
                    true
                }

                else -> false
            }
        }
    }

    private fun openItemFragment(
        fragmentManager: FragmentManager,
        itemType: String,
        isLandscape: Boolean
    ) {
        val fragment = ItemFragmentNavigator.newInstance(itemType)

        if (isLandscape) {
            fragmentManager.beginTransaction()
                .replace(R.id.itemFragment, fragment)
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .add(R.id.mainFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}