package com.example.myapplication.activity

import android.os.Bundle
import com.example.myapplication.fragment.ItemFragment

object ItemFragmentNavigator {


    const val EXTRA_TYPE = "extra_type"
    const val EXTRA_ITEM = "extra_item"
    const val EXTRA_CONDITION = "extra_condition"

    fun newInstance(itemType: String): ItemFragment {
        return ItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_TYPE, itemType)
            }
        }
    }


}