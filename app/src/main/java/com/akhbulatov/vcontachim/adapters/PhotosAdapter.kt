package com.akhbulatov.vcontachim.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ItemPhotoBinding
import com.akhbulatov.vcontachim.model.Photos
import com.bumptech.glide.Glide

class PhotosAdapter : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {
    var photos: List<Photos.Item> = emptyList()

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
        val photo: Photos.Item = photos[position]
        val sizes: Photos.Size = photo.sizes[0]

        Glide.with(holder.itemView)
            .load(sizes.photo)
            .into(holder.binding.photo)

        val likes: Photos.Likes = photo.likes

        val comments = photo.comments

        val reposts = photo.reposts

        holder.itemView.setOnClickListener {
            VcontachimApplication.router.replaceScreen(Screens.photoAc())

            VcontachimApplication.router.navigateTo(Screens.photoFr(likes,comments,reposts,sizes))
        }
    }
}