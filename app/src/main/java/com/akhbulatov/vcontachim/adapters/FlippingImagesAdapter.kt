package com.akhbulatov.vcontachim.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemFlippingImagesBinding
import com.akhbulatov.vcontachim.model.News
import com.bumptech.glide.Glide

class FlippingImagesAdapter :
    ListAdapter<News.Attachment, FlippingImagesAdapter.FlippingImagesViewHolder>(FlippingImagesDuffCallback) {

    class FlippingImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemFlippingImagesBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlippingImagesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_flipping_images,
            parent,
            false
        )

        return FlippingImagesViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: FlippingImagesViewHolder, position: Int) {
        val flippingImages: News.Attachment = getItem(position)

        Glide.with(holder.itemView)
            .load(flippingImages.photo?.sizes?.getOrNull(0)?.url)
            .into(holder.binding.image)
    }

    object FlippingImagesDuffCallback : DiffUtil.ItemCallback<News.Attachment>() {

        override fun areItemsTheSame(oldItem: News.Attachment, newItem: News.Attachment): Boolean {
            return oldItem.photo == newItem.photo
        }

        override fun areContentsTheSame(oldItem: News.Attachment, newItem: News.Attachment): Boolean {
            return oldItem == newItem
        }
    }

}