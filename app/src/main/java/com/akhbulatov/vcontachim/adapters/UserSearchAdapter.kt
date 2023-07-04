package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemUserSearchBinding
import com.akhbulatov.vcontachim.model.UserSearchUi
import com.bumptech.glide.Glide

class UserSearchAdapter(private val friend: FriendListener) :
    ListAdapter<UserSearchUi, UserSearchAdapter.UserSearchViewHolder>(UserSearchDuffCallback) {

    class UserSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserSearchBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_user_search,
            parent,
            false
        )
        return UserSearchViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserSearchViewHolder, position: Int) {
        val item: UserSearchUi = getItem(position)

        Glide.with(holder.itemView)
            .load(item.photo200)
            .into(holder.binding.avatar48)

        if (item.online == 1) holder.binding.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
        else holder.binding.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)

        if (item.verified == 1) holder.binding.verified16.setImageResource(R.drawable.ic_verified)
        else holder.binding.verified16.setImageResource(R.drawable.ic_android_black_24dp)

        if (item.isFriend == 1) holder.binding.addFriend.setImageResource(R.drawable.done_24__1_)
        else holder.binding.addFriend.setImageResource(R.drawable.user_add_outline_56__3_)

        holder.binding.nameSurname.text = "${item.firstName} ${item.lastName}"
        holder.binding.description.text = item.description

        holder.binding.addFriend.setOnClickListener {
            this.friend.searchFriend(item)
        }
    }

    interface FriendListener {
        fun searchFriend(item: UserSearchUi)
    }

    object UserSearchDuffCallback : DiffUtil.ItemCallback<UserSearchUi>() {

        override fun areItemsTheSame(
            oldItem: UserSearchUi,
            newItem: UserSearchUi
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: UserSearchUi,
            newItem: UserSearchUi
        ): Boolean {
            return oldItem == newItem
        }
    }
}