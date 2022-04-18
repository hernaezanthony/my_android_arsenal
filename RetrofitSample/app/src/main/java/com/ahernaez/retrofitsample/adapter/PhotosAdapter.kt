package com.ahernaez.retrofitsample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahernaez.retrofitsample.R
import com.ahernaez.retrofitsample.databinding.RowPhotoBinding
import com.ahernaez.retrofitsample.model.Photo
import com.bumptech.glide.Glide

class PhotosAdapter(private val context: Context, private val photos: ArrayList<Photo>) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowPhotoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val photo = photos[position]

        with(holder){
            with(photo){
                binding.authorTextView.text = this.author

                Glide.with(context)
                    .load(this.download_url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(binding.photoImageView)
            }
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }

}