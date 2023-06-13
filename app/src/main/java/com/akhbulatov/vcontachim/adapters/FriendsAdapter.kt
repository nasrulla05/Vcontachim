package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemFriendBinding
import com.akhbulatov.vcontachim.model.Friends
import com.bumptech.glide.Glide

class FriendsAdapter(val photo:ClickOfAvatarListener) :
    ListAdapter<Friends.Item, FriendsAdapter.FriendsViewHolder>(FriendsDiffCallback) {

    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemFriendBinding = ItemFriendBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(
            R.layout.item_friend,
            parent,
            false
        )

        return FriendsViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val friend: Friends.Item = getItem(position)

        Glide.with(holder.itemView)
            .load(friend.avatar100)
            .into(holder.binding.avatar48)

        holder.binding.nameLastName.text =
            "${friend.firstName} ${friend.lastName}"

        holder.binding.avatar48.setOnClickListener {
            this.photo.click(friend)
        }
    }

    interface ClickOfAvatarListener{
        fun click(friends: Friends.Item)
    }

    object FriendsDiffCallback : DiffUtil.ItemCallback<Friends.Item>() {

        override fun areItemsTheSame(oldItem: Friends.Item, newItem: Friends.Item): Boolean {
            return oldItem.lastName == newItem.lastName
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Friends.Item, newItem: Friends.Item): Boolean {
            return oldItem == newItem
        }
    }
}