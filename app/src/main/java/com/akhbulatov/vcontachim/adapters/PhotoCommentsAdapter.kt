package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemCommentBinding
import com.akhbulatov.vcontachim.model.PhotoCommentsUi
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

class PhotoCommentsAdapter(private val likeComment: OnClick) :
    ListAdapter<PhotoCommentsUi, PhotoCommentsAdapter.PhotoCommentsViewHolder>(
        PhotoCommentsDiffCallback
    ) {

    class PhotoCommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCommentBinding.bind(itemView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoCommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_comment,
            parent,
            false
        )
        return PhotoCommentsViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(
        holder: PhotoCommentsViewHolder,
        position: Int,
    ) {
        val textComm: PhotoCommentsUi = getItem(position)

        holder.binding.nameSurname.text =
            "${textComm.firstName} ${textComm.lastName}"
        holder.binding.comment.text = textComm.textComm

        Glide.with(holder.itemView)
            .load(textComm.photo)
            .into(holder.binding.avatar)

        val formatter = SimpleDateFormat("d MMMM yyyy")
        val date = formatter.format(textComm.date * 1000L)
        holder.binding.date.text = date

        if (textComm.online == 1) {
            holder.binding.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
        } else {
            holder.binding.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)
        }

        holder.binding.like.setOnClickListener {
            this.likeComment.likeComm(textComm)
        }

        if (textComm.usersLike == 1L) {
            holder.binding.like.setImageResource(R.drawable.like_filled_red_28)
            holder.binding.like.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                ), android.graphics.PorterDuff.Mode.MULTIPLY
            )
        } else holder.binding.like.setImageResource(R.drawable.ic_like21)

    }

    interface OnClick {
        fun likeComm(commentsUi: PhotoCommentsUi)
    }

    object PhotoCommentsDiffCallback :
        DiffUtil.ItemCallback<PhotoCommentsUi>() {

        override fun areItemsTheSame(
            oldItem: PhotoCommentsUi,
            newItem: PhotoCommentsUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoCommentsUi,
            newItem: PhotoCommentsUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}