package com.ahernaez.viewmodelwithstate.views.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.ahernaez.viewmodelwithstate.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory()
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
        initUI()
    }

    private fun initUI(){

        binding.getRandomWordBtn.setOnClickListener {
            mainViewModel.getRandomWord()
        }
    }

    private fun setUpViewModel(){

        mainViewModel.apply {
            getState().observe(this@MainActivity){
                renderUiState(it)
            }
        }
    }

    private fun renderUiState(state: MainState){

        when(state){

            is MainState.Loading -> {

                binding.progressBar.visibility = View.VISIBLE

                Log.d("STATE_DATA", "LOADING")
            }
            is MainState.RandomWord -> {

                binding.progressBar.visibility = View.GONE
                binding.wordTextView.text = state.data[0].word
                binding.definitionTextView.text = state.data[0].definition

                Log.d("STATE_DATA", Gson().toJson(state.data))
            }
            is MainState.Error -> {

                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, state.errorString, Toast.LENGTH_SHORT).show()

                Log.d("STATE_DATA", state.errorString)
            }
        }
    }
}