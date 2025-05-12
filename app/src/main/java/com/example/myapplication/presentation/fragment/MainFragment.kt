package com.example.myapplication.presentation.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.common.navigation.ItemFragmentNavigator
import com.example.myapplication.presentation.adapter.LibraryAdapter
import com.example.myapplication.common.util.LibraryItemTouchHelper
import com.example.myapplication.presentation.viewmodel.MainViewModel
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

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val title = binding.searchTitleEditText.text.toString()
            val author = binding.searchAuthorEditText.text.toString()
            binding.searchButton.isEnabled = title.length >= 3 || author.length >= 3
        }
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

        binding.buttonMyLibrary.setOnClickListener {
            viewModel.setSearchMode(false)
            viewModel.loadMyLibrary()
        }

        binding.buttonGoogleBooks.setOnClickListener {
            viewModel.setSearchMode(true)
            viewModel.clearLibraryView()
            binding.shimmerLayout.stopShimmer()
            binding.shimmerLayout.visibility = View.GONE
            binding.recyclerView.visibility = View.GONE
        }

        binding.searchTitleEditText.addTextChangedListener(textWatcher)
        binding.searchAuthorEditText.addTextChangedListener(textWatcher)

        binding.searchButton.setOnClickListener {
            val title = binding.searchTitleEditText.text.toString()
            val author = binding.searchAuthorEditText.text.toString()
            if (title.length >= 3 || author.length >= 3) {
                viewModel.setSearchMode(false)
                viewModel.searchBooks(title, author)
            }
        }

        viewModel.libraryLoaded.onEach { loaded ->
            if (loaded) {
                binding.apply {
                    shimmerLayout.visibility = View.VISIBLE
                    shimmerLayout.startShimmer()
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        fragmentAdapter = LibraryAdapter(
            onClickItem = { item ->
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
            },
            onLongClickItem = { item ->
                viewModel.saveItemToDatabase(item)
                Toast.makeText(
                    requireContext(),
                    "${item.name} сохранен в библиотеку",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
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
            shimmerLayout.visibility = View.GONE
            recyclerView.visibility = View.GONE
            toolbar.visibility = View.VISIBLE
        }

        viewModel.searchMode.onEach { searchMode ->
            if (searchMode!!) {
                binding.searchLayout.visibility = View.VISIBLE
            } else  binding.searchLayout.visibility = View.GONE
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.loading.onEach { loading ->
            if (loading == true) {
                binding.shimmerLayout.visibility = View.VISIBLE
                binding.shimmerLayout.startShimmer()
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
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