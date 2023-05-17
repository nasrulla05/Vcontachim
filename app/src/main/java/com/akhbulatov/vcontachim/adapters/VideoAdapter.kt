package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.VcontachimApplication
import com.akhbulatov.vcontachim.databinding.ItemVideoBinding
import com.akhbulatov.vcontachim.model.Video
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

class VideoAdapter(private val itemVideo:DeleteVideo) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    var videos: List<Video.Item> = emptyList()

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

    override fun getItemCount() = videos.size

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video: Video.Item = videos[position]
        val sizeVideo: Video.Image = video.image[0]

        Glide.with(holder.itemView)
            .load(sizeVideo.photo)
            .into(holder.binding.itemVideo)

        holder.binding.moreVertical.setOnClickListener {
            itemVideo.onDeleteClick(video)
        }

        holder.binding.moreVertical
        val plurals = VcontachimApplication.context.resources.getQuantityString(
            R.plurals.plurals_video,
            video.views
        )
        holder.binding.nameVideo.text = video.title
        holder.binding.numbersViews.text = "${video.views} $plurals"

        val hours = video.duration / 3600
        val min = (video.duration % 3600) / 60
        val sec = video.duration % 60

        val time = String.format("$hours:$min:$sec")
        holder.binding.duration.text = time

        val formatter = SimpleDateFormat("d MMMM yyyy")
        val date = formatter.format(video.date * 1000L)
        holder.binding.releaseDate.text = date
    }

    interface DeleteVideo {
        fun onDeleteClick(video:Video.Item)
    }
}