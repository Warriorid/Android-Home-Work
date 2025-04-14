package com.example.myapplication.activity

import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentMainBinding


fun toolbarMenu(
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

private fun openItemFragment(fragmentManager: FragmentManager, itemType: String, isLandscape: Boolean) {
    val fragment = ItemActivityNavigator.newInstance(itemType)

    if (isLandscape) {
            fragmentManager.beginTransaction()
            .replace(R.id.itemFragment, fragment)
            .commit()
    }
    else {
            fragmentManager.beginTransaction()
            .replace(R.id.mainFragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}