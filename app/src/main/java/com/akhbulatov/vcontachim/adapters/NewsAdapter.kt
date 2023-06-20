package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemNewsBinding
import com.akhbulatov.vcontachim.model.NewsUI
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

class NewsAdapter(val addDeleteLike: LikeDeletePostListener) :
    ListAdapter<NewsUI, NewsAdapter.NewsViewHolder>(NewsDuffCallback) {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemNewsBinding = ItemNewsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_news,
            parent,
            false
        )

        return NewsViewHolder(itemView = itemView)
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n", "CheckResult", "ResourceAsColor")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsUI = getItem(position)

        Glide.with(holder.itemView)
            .load(news.photo200)
            .into(holder.binding.avatar32DP)

        holder.binding.title.text = news.name


        val formatter = SimpleDateFormat("d MMM в k:mm")
        val date = formatter.format(news.date?.times(1000L) ?: "")
        holder.binding.date.text = date

        Glide.with(holder.itemView)
            .load(news.postUrl)
            .into(holder.binding.photo)

        holder.binding.countLike.text = news.countLike?.toString()
        holder.binding.countComm.text = news.countComm?.toString()
        holder.binding.countReposts.text = news.repostsCount?.toString()
        holder.binding.countViews.text = news.view?.toString()

        holder.binding.clickLike.setOnClickListener {
            this.addDeleteLike.addDeleteLikePostClick(news)
        }

        if (news.userLikes == 1) {
            holder.binding.clickLike.setBackgroundResource(R.drawable.like)
            holder.binding.like.setImageResource(R.drawable.group_16)
            holder.binding.countLike.setTextColor(Color.parseColor("#E6457A"))
        } else {
            holder.binding.clickLike.setBackgroundResource(R.drawable.count_likes_comments_reposts)
            holder.binding.like.setImageResource(R.drawable.like_outline_24)
            holder.binding.countLike.setTextColor(Color.parseColor("#818C99"))
        }
    }

    interface LikeDeletePostListener {
        fun addDeleteLikePostClick(news: NewsUI)
    }

    object NewsDuffCallback : DiffUtil.ItemCallback<NewsUI>() {

        override fun areItemsTheSame(oldItem: NewsUI, newItem: NewsUI): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NewsUI, newItem: NewsUI): Boolean {
            return oldItem == newItem
        }

    }
}


