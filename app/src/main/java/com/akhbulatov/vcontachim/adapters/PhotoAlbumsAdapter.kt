package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ItemPhotoAlbumsBinding
import com.akhbulatov.vcontachim.model.PhotosAlbums
import com.bumptech.glide.Glide

class PhotoAlbumsAdapter : RecyclerView.Adapter<PhotoAlbumsAdapter.PhotosAlbumsViewHolder>() {
    var photos: List<PhotosAlbums.Items> = emptyList()

    class PhotosAlbumsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemPhotoAlbumsBinding = ItemPhotoAlbumsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosAlbumsViewHolder {
        val layoutInflate: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflate.inflate(
            R.layout.item_photo_albums,
            parent,
            false
        )
        return PhotosAlbumsViewHolder(itemView)
    }

    override fun getItemCount() = photos.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PhotosAlbumsViewHolder, position: Int) {
        val photo: PhotosAlbums.Items = photos[position]

        Glide.with(holder.itemView)
            .load(photo.avatar)
            .into(holder.binding.avatar156)
        holder.binding.nameYer.text = photo.title
        val plurals = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.plurals_photo_albums,
            photo.sizePhoto
        )
        holder.binding.quantityPhoto.text = "${photo.sizePhoto} $plurals"

        holder.binding.itemPhotoAlbums.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.photosFr(photo))
        }
    }
}