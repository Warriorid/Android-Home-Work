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



        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, MainFragment())
                .commit()

            if (viewModel.selectedItem.value != null || viewModel.getItemType() != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.itemFragment, ItemFragment())
                    .commit()
            }
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFragment, MainFragment())
                    .commit()
            if (viewModel.getItemType() != null || viewModel.selectedItem.value != null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.mainFragment, ItemFragment())
                    .addToBackStack("itemFragment")
                    .commit()
            }
        }

    }

}