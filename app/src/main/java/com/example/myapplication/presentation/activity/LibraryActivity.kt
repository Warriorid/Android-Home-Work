package com.example.myapplication.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.api.RetrofitHelper
import com.example.myapplication.data.repository.DataRepository
import com.example.myapplication.data.db.MainDB
import com.example.myapplication.data.mapper.BookMapper
import com.example.myapplication.data.mapper.GoogleBooksMapper
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.fragment.ItemFragment
import com.example.myapplication.presentation.fragment.MainFragment
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.presentation.viewmodel.ViewModelFactory


class LibraryActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var isLandscape: Boolean = false
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val dao = MainDB.getDB(this)
        val bookMapper = BookMapper
        val newspaperMapper = com.example.myapplication.data.mapper.NewspaperMapper
        val diskMapper = com.example.myapplication.data.mapper.DiskMapper
        val googleBooksMapper = GoogleBooksMapper()
        val googleBooksApi = RetrofitHelper.googleBooksApi
        val repository = DataRepository(
            libraryDao = dao.getDb(),
            googleBooksApi = googleBooksApi,
            mapper = googleBooksMapper,
            bookMapper = bookMapper,
            newspaperMapper = newspaperMapper,
            diskMapper = diskMapper
        )

        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        viewModel = ViewModelProvider(this, ViewModelFactory(repository))[MainViewModel::class.java]



        if (isLandscape) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFragment, MainFragment())
                .commit()

            if (viewModel.selectedItem.value == null && viewModel.getItemType() == null) {
                supportFragmentManager.findFragmentById(R.id.itemFragment)?.let {
                    supportFragmentManager.beginTransaction()
                        .remove(it)
                        .commit()
                }
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } else {
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
                    .replace(R.id.mainFragment, ItemFragment())
                    .addToBackStack("itemFragment")
                    .commit()
            }
        }

    }

}