package com.ahernaez.roompersistence.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.ahernaez.roompersistence.databinding.RowItemBinding
import com.ahernaez.roompersistence.model.Language

class ListAdapter(private val list : ArrayList<Language>,private val onEditBtnClickListener: OnEditBtnClickListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>(), Filterable {

    inner class ViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    var filteredList = ArrayList<Language>()

    init {
        filteredList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = filteredList[position]

        with(holder){
            with(item){
                binding.itemTextView.text = this.languageName

                binding.editBtn.setOnClickListener {
                    onEditBtnClickListener.onEditBtnClicked(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    override fun getFilter(): Filter {

        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var query = constraint.toString()

                if (query.isEmpty()){
                    filteredList = list
                }
                else{

                    val result = ArrayList<Language>()

                    for (string in list){
                        if (string.languageName.lowercase().contains(query.lowercase())){
                            result.add(string)
                        }
                    }

                    filteredList = result
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults

            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filteredList = results?.values as ArrayList<Language>
                notifyDataSetChanged()
            }

        }
    }

    interface OnEditBtnClickListener{
        fun onEditBtnClicked(language: Language)
    }

}