package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemVideoCommentBinding
import com.akhbulatov.vcontachim.model.VideoCommentsUI
import java.text.SimpleDateFormat

class VideoCommAdapter :
    ListAdapter<VideoCommentsUI, VideoCommAdapter.VideoCommViewHolder>(VideoCommentsDuffCallback) {
    class VideoCommViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemVideoCommentBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoCommViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_video_comment,
            parent,
            false
        )
        return VideoCommViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: VideoCommViewHolder, position: Int) {
        val item: VideoCommentsUI = getItem(position)

        holder.binding.nameSurname.text = "${item.firstName} ${item.surName}"
        holder.binding.comment.text = item.text

        val formatter = SimpleDateFormat("d MMM в k:mm")
        val date = formatter.format(item.date * 1000L)
        holder.binding.date.text = date

    }

    object VideoCommentsDuffCallback : DiffUtil.ItemCallback<VideoCommentsUI>() {
        override fun areItemsTheSame(oldItem: VideoCommentsUI, newItem: VideoCommentsUI): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: VideoCommentsUI,
            newItem: VideoCommentsUI
        ): Boolean {
            return oldItem == newItem
        }
    }
}