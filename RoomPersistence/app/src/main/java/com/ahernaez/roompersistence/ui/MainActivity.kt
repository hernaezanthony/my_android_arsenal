package com.ahernaez.roompersistence.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahernaez.roompersistence.databinding.ActivityMainBinding
import com.ahernaez.roompersistence.model.Language
import com.ahernaez.roompersistence.utils.SwipeToDeleteCallback
import com.ahernaez.roompersistence.viewmodel.LanguageViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListAdapter
    private var list = ArrayList<String>()

    private lateinit var languageViewModel: LanguageViewModel
    private var languageList = ArrayList<Language>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        languageViewModel = ViewModelProvider(this).get(LanguageViewModel::class.java)

        setUpRecyclerView()
        addDividerDecoration()
        setUpSwipeToDelete()
        setUpSearch()
        setUpSwipeRefresh()
    }

    private fun addData(){

        list.addAll(arrayListOf("C#", "Go","Java", "Javascript", "Kotlin", "Python", "Ruby", "Swift"))

        for (string in list){
            val newLanguage = Language(0, string)
            addLanguage(newLanguage)
        }
    }

    private fun addLanguage(language: Language){

        languageViewModel.insertLanguage(this, language)
    }

    private fun getLanguageList(){

        languageViewModel.getLanguageList(this)!!.observe(this, Observer{ langList ->

            if (langList.isEmpty()){
                addData()
            }

            languageList.clear()
            languageList.addAll(langList)
            listAdapter.notifyDataSetChanged()

        })
    }

    private fun setUpRecyclerView(){

        layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(languageList)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = listAdapter

        getLanguageList()

    }

    private fun addDividerDecoration(){

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                baseContext,
                layoutManager.orientation
            )
        )
    }

    private fun setUpSwipeToDelete(){

        val handler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val index = viewHolder.adapterPosition
                list.removeAt(index)
                listAdapter.notifyItemRemoved(index)
            }
        }

        val itemTouchHelper = ItemTouchHelper(handler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun setUpSearch(){

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                listAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setUpSwipeRefresh(){

        binding.swipeRefreshLayout.setOnRefreshListener {
            list.clear()
            getLanguageList()
            listAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}