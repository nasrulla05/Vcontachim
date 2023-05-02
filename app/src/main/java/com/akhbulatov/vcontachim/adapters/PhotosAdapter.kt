package com.akhbulatov.vcontachim.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemPhotoBinding
import com.akhbulatov.vcontachim.model.Photos
import com.bumptech.glide.Glide

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    var photos: List<Photos.Size> = emptyList()

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotoBinding = ItemPhotoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(
            R.layout.item_photo,
            parent,
            false
        )
        return PhotosViewHolder(itemView)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
       val photo:Photos.Size = photos[position]
        val sizes:Photos.Size = 

        Glide.with(holder.itemView)
            .load(size.photo)
            .into(holder.binding.photo)
    }
}