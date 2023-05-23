package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.Screens
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ItemVideoBinding
import com.akhbulatov.vcontachim.model.Video
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

class VideoAdapter(
    private val itemVideo: DeleteVideoListener) :
    ListAdapter<Video.Item, VideoAdapter.VideoViewHolder>(VideoDiffCallback) {

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemVideoBinding = ItemVideoBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_video,
            parent,
            false
        )
        return VideoViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val itemVideo: Video.Item = getItem(position)

        Glide.with(holder.itemView)
            .load(itemVideo.image[0].photo)
            .into(holder.binding.itemVideo)

        holder.binding.moreVertical.setOnClickListener {
            this.itemVideo.onDeleteClick(itemVideo)
        }

        holder.binding.video.setOnClickListener {
            VcontachimApplication.router.navigateTo(Screens.videoPlayerFr(itemVideo))
        }
        val plurals = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.plurals_video,
            itemVideo.views
        )
        holder.binding.nameVideo.text = itemVideo.title
        holder.binding.numbersViews.text = "${itemVideo.views} $plurals"

        val hours = itemVideo.duration / 3600
        val min = (itemVideo.duration % 3600) / 60
        val sec = itemVideo.duration % 60

        val time = String.format("$hours:$min:$sec")
        holder.binding.duration.text = time

        val formatter = SimpleDateFormat("d MMMM yyyy")
        val date = formatter.format(itemVideo.date * 1000L)
        holder.binding.releaseDate.text = date
    }

    interface DeleteVideoListener {
        fun onDeleteClick(video: Video.Item)
    }

    object VideoDiffCallback : DiffUtil.ItemCallback<Video.Item>() {
        override fun areItemsTheSame(oldItem: Video.Item, newItem: Video.Item): Boolean {
            return oldItem.itemId == newItem.itemId
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Video.Item, newItem: Video.Item): Boolean {
            return oldItem == newItem
        }
    }
}