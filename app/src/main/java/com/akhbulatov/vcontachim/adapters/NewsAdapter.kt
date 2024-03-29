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
import com.akhbulatov.vcontachim.model.NewsUi
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat

class NewsAdapter(private val addDeleteLike: LikeDeletePostListener) :
    ListAdapter<NewsUi, NewsAdapter.NewsViewHolder>(NewsDuffCallback) {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemNewsBinding = ItemNewsBinding.bind(itemView)
        val adapter = FlippingImagesAdapter()

        init {
            binding.viewPager2.adapter = adapter
        }
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

    @SuppressLint("SimpleDateFormat", "ResourceAsColor")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news: NewsUi = getItem(position)

        holder.binding.apply {

            Glide.with(holder.itemView)
                .load(news.photo)
                .into(avatar32DP)

            title.text = news.name

            val formatter = SimpleDateFormat("d MMM в k:mm")
            val date2 = formatter.format(news.date?.times(1000L) ?: "")

            date.text = date2
            countLike.text = news.countLike?.toString()
            countComm.text = news.countComm?.toString()
            countReposts.text = news.repostsCount?.toString()
            countViews.text = news.view?.toString()
            text.text = news.text

            holder.adapter.submitList(news.photoList)
            indicator.setupWithViewPager(holder.binding.viewPager2)

            if (news.photoList?.size == 1)background.visibility = View.GONE
            else background.visibility = View.VISIBLE

            if (news.userLikes == 1) {
                clickLike.setBackgroundResource(R.drawable.like)
                like.setImageResource(R.drawable.group_16)
                countLike.setTextColor(Color.parseColor("#E6457A"))
            } else {
                clickLike.setBackgroundResource(R.drawable.count_likes_comments_reposts)
                like.setImageResource(R.drawable.like_outline_24)
                countLike.setTextColor(Color.parseColor("#818C99"))
            }

            if (news.verified == 1) verified16.visibility = View.VISIBLE
            else verified16.visibility = View.GONE
        }

        holder.binding.clickLike.setOnClickListener {
            this.addDeleteLike.addDeleteLikePostClick(news)
        }
    }

    interface LikeDeletePostListener {
        fun addDeleteLikePostClick(news: NewsUi)
    }

    object NewsDuffCallback : DiffUtil.ItemCallback<NewsUi>() {

        override fun areItemsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: NewsUi, newItem: NewsUi): Boolean {
            return oldItem == newItem
        }

    }
}