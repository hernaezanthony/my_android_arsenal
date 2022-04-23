package com.ahernaez.commonrecyclerview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahernaez.commonrecyclerview.databinding.ActivityMainBinding
import com.ahernaez.commonrecyclerview.utils.SwipeToDeleteCallback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var listAdapter: ListAdapter
    private var list = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addData()
        setUpRecyclerView()
        addDividerDecoration()
        setUpSwipeToDelete()
        setUpSearch()
        setUpSwipeRefresh()
    }

    private fun addData(){

        list.addAll(arrayListOf("C#", "Go","Java", "Javascript", "Kotlin", "Python", "Ruby", "Swift"))
    }

    private fun setUpRecyclerView(){

        layoutManager = LinearLayoutManager(this)
        listAdapter = ListAdapter(list)

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = listAdapter

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
            addData()
            listAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }
}