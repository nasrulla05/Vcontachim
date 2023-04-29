package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemPhotoBinding
import com.akhbulatov.vcontachim.model.Photos
import com.bumptech.glide.Glide

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    var photos: List<Photos.Items> = emptyList()

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotoBinding = ItemPhotoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val layoutInflate: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflate.inflate(
            R.layout.item_photo,
            parent,
            false
        )
        return PhotosViewHolder(itemView)
    }

    override fun getItemCount() = photos.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val photo: Photos.Items = photos[position]

        Glide.with(holder.itemView)
            .load(photo.avatar)
            .into(holder.binding.avatar156)
        holder.binding.season.text = photo.title
        if (photo.sizePhoto == 0) {
            holder.binding.quantityPhoto.text = "${photo.sizePhoto} фотографий"
        } else if (photo.sizePhoto == 1) {
            holder.binding.quantityPhoto.text = "${photo.sizePhoto} фотография"
        } else if (photo.sizePhoto!! >= 4) {
            holder.binding.quantityPhoto.text = "${photo.sizePhoto} фотографии"
        } else {
            holder.binding.quantityPhoto.text = "${photo.sizePhoto} фотографий"
        }
    }
}