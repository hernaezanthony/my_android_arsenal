package com.ahernaez.retrofitsample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ahernaez.retrofitsample.adapter.PhotosAdapter
import com.ahernaez.retrofitsample.databinding.ActivityMainBinding
import com.ahernaez.retrofitsample.model.Photo
import com.ahernaez.retrofitsample.utils.PaginationScrollListener
import com.ahernaez.retrofitsample.viewmodel.MainState
import com.ahernaez.retrofitsample.viewmodel.MainViewModel
import com.ahernaez.retrofitsample.viewmodel.MainViewModelFactory
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory(this)
    }
    private lateinit var photosAdapter: PhotosAdapter

    private val photos = arrayListOf<Photo>()
    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setUpViewModel()
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

    private fun setUpViewModel(){

        mainViewModel.getState().observe(this, Observer {
            renderUiState(it)
        })
        
    }

    private fun getData(){

        mainViewModel.getPhotoList(currentPage, 15)
    }

    private fun renderUiState(state: MainState){

        when(state){

            is MainState.Loading -> {
                isLoading = true
                binding.progressBar.visibility = View.VISIBLE
            }

            is MainState.PhotoList -> {

                if (!state.data.isNullOrEmpty()){

                    Log.d(TAG, Gson().toJson(state.data))

                    photos.addAll(state.data)
                    photosAdapter.notifyDataSetChanged()
                }
                isLoading = false
                binding.progressBar.visibility = View.GONE
            }

            is MainState.Error -> {
                isLoading = false
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this@MainActivity, state.errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }
}