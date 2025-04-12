package com.example.myapplication.activity

import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding


fun toolbarMenu(
    binding: FragmentMainBinding,
    viewModel: MainViewModel,
    fragmentManager: FragmentManager
) {
    binding.toolbar.setOnMenuItemClickListener { menuItem ->
        viewModel.clearSelectedItem()
        when (menuItem.itemId) {
            R.id.action_book -> {
                viewModel.setItemType("Book")
                openItemFragment(fragmentManager, "Book")
                true
            }

            R.id.action_newspaper -> {
                viewModel.setItemType("Newspaper")
                openItemFragment(fragmentManager, "Newspaper")
                true
            }

            R.id.action_disk -> {
                viewModel.setItemType("Disk")
                openItemFragment(fragmentManager, "Disk")
                true
            }

            else -> false
        }
    }
}

private fun openItemFragment(fragmentManager: FragmentManager, itemType: String) {
    val fragment = ItemActivityNavigator.newInstance(itemType)
    fragmentManager.beginTransaction()
        .add(R.id.mainFragment, fragment)
        .addToBackStack(null)
        .commit()
}