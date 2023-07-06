package com.akhbulatov.vcontachim.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemCommentsBinding
import com.akhbulatov.vcontachim.model.PhotoCommentsUi

class CommentsAdapter:ListAdapter<PhotoCommentsUi,CommentsAdapter.CommentsViewHolder>(CommentsDiffCallback) {

    class CommentsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val binding = ItemCommentsBinding.bind(itemView)
    }

    object CommentsDiffCallback:DiffUtil.ItemCallback<PhotoCommentsUi>(){

        override fun areItemsTheSame(oldItem: PhotoCommentsUi, newItem: PhotoCommentsUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PhotoCommentsUi,
            newItem: PhotoCommentsUi
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_comments,
            parent,
            false
        )
        return CommentsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {

    }


}