package com.example.myapplication.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.activity.LibraryAdapter
import com.example.myapplication.activity.LibraryItemTouchHelper
import com.example.myapplication.activity.MainViewModel
import com.example.myapplication.activity.toolbarMenu
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
        }
        viewModel.item.observe(viewLifecycleOwner) { items ->
            fragmentAdapter.submitList(items)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}