package com.example.myapplication.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.activity.ItemFragmentNavigator
import com.example.myapplication.activity.LibraryAdapter
import com.example.myapplication.activity.LibraryItemTouchHelper
import com.example.myapplication.activity.MainViewModel
import com.example.myapplication.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var fragmentAdapter: LibraryAdapter
    private val viewModel: MainViewModel by activityViewModels()
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
                    .replace(R.id.mainFragment, ItemFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
        binding.recyclerView.apply {
            adapter = fragmentAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val swipeDeleted = LibraryItemTouchHelper(fragmentAdapter, viewModel)
            ItemTouchHelper(swipeDeleted).attachToRecyclerView(this)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                    viewModel.checkPagination(lastVisibleItemPosition)
                    viewModel.checkPagination(firstVisibleItemPosition)
                }
            })
        }


        binding.apply {
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
            toolbar.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }

        viewModel.loading.onEach { loading ->
            if (loading == true) {
                binding.apply {
                    shimmerLayout.visibility = View.VISIBLE
                    shimmerLayout.startShimmer()
                    toolbar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                }
            } else {
                binding.apply {
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.item.onEach { item ->
            fragmentAdapter.submitList(item)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.error.onEach { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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
                R.id.action_sort_by_name -> {
                    viewModel.sortItems("name")
                    true
                }
                R.id.action_sort_by_date -> {
                    viewModel.sortItems("date")
                    true
                }else -> false
            }
        }
    }

    private fun openItemFragment(
        fragmentManager: FragmentManager,
        itemType: String,
        isLandscape: Boolean
    ) {
        val fragment = ItemFragmentNavigator.newItemFragment(itemType)

        if (isLandscape) {
            fragmentManager.beginTransaction()
                .replace(R.id.itemFragment, fragment)
                .commit()
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.mainFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}