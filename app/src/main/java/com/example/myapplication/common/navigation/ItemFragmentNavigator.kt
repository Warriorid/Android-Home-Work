package com.example.myapplication.common.navigation

import android.os.Bundle
import com.example.myapplication.presentation.fragment.ItemFragment

object ItemFragmentNavigator {


    const val EXTRA_TYPE = "extra_type"

    fun newItemFragment(itemType: String): ItemFragment {
        return ItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_TYPE, itemType)
            }
        }
    }


}