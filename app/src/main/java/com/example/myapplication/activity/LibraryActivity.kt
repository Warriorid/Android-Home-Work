package com.example.myapplication.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragment.ItemFragment
import com.example.myapplication.fragment.MainFragment


class LibraryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var isLandscape: Boolean = false
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        val shouldRestoreItemFragment = (savedInstanceState != null && viewModel.isItemFragmentOpen) ||
                (viewModel.selectedItem.value != null && !isLandscape)

        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, MainFragment.newInstance())
                .commit()

            if (viewModel.selectedItem.value != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.itemFragment, ItemFragment())
                    .commit()
                viewModel.isItemFragmentOpen = true
            }
        } else {
            if (shouldRestoreItemFragment) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFragment, ItemFragment())
                    .addToBackStack(null)
                    .commit()
                viewModel.isItemFragmentOpen = true
            } else {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFragment, MainFragment.newInstance())
                    .commit()
                viewModel.isItemFragmentOpen = false
            }
        }

    }

}