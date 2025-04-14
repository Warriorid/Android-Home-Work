package com.example.myapplication.activity

import android.os.Bundle
import com.example.myapplication.consoleapp.LibraryItem
import com.example.myapplication.fragment.ItemFragment

object ItemActivityNavigator {


    const val EXTRA_TYPE = "extra_type"
    const val EXTRA_ITEM = "extra_item"
    const val EXTRA_CONDITION = "extra_condition"

//    fun newInstance(item: LibraryItem): ItemFragment {
//        return ItemFragment().apply {
//            arguments = Bundle().apply {
//                putParcelable(EXTRA_ITEM, item)
//            }
//        }
//
//    }

    fun newInstance(itemType: String): ItemFragment {
        return ItemFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_TYPE, itemType)
            }
        }
    }


}