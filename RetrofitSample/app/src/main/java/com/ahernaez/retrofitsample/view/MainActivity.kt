package com.ahernaez.retrofitsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ahernaez.retrofitsample.R
import com.ahernaez.retrofitsample.adapter.PhotosAdapter
import com.ahernaez.retrofitsample.databinding.ActivityMainBinding
import com.ahernaez.retrofitsample.model.Photo
import com.ahernaez.retrofitsample.utils.PaginationScrollListener
import com.ahernaez.retrofitsample.viewmodel.MainViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var photosAdapter: PhotosAdapter

    private val photos = arrayListOf<Photo>()
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setUpRecyclerView()
        getData()
    }

    private fun setUpRecyclerView(){

        val layoutManager = GridLayoutManager(this, 3)
        photosAdapter = PhotosAdapter(this, photos)

        binding.photosRV.layoutManager = layoutManager
        binding.photosRV.adapter = photosAdapter

        binding.photosRV.addOnScrollListener(object : PaginationScrollListener(layoutManager){
            override fun loadMoreItems() {
                isLoading = true
                currentPage++
                getData()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

    }

    private fun getData(){

        mainViewModel.getPhotoList(currentPage, 15).observe(this, Observer { list ->

            if (!list.isNullOrEmpty()){

                Log.d(TAG, Gson().toJson(list))

                photos.addAll(list)
                photosAdapter.notifyDataSetChanged()
            }

            isLoading = false
        })
    }
}