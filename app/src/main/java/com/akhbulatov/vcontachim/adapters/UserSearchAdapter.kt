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
import com.akhbulatov.vcontachim.model.UsersSearch
import com.bumptech.glide.Glide

class UserSearchAdapter :
    ListAdapter<UsersSearch.Item, UserSearchAdapter.UserSearchViewHolder>(UserSearchDuffCallback) {

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
        val item: UsersSearch.Item = getItem(position)

        Glide.with(holder.itemView)
            .load(item.profile?.photo200)
            .into(holder.binding.avatar48)

        if (item.profile?.online == 1) holder.binding.onlineOrOffline.setImageResource(R.drawable.online_composite_16)
        else holder.binding.onlineOrOffline.setImageResource(R.drawable.ic_android_black_24dp)

        holder.binding.nameSurname.text = "${item.profile?.firstName} ${item.profile?.lastName}"
    }

    object UserSearchDuffCallback : DiffUtil.ItemCallback<UsersSearch.Item>() {

        override fun areItemsTheSame(
            oldItem: UsersSearch.Item,
            newItem: UsersSearch.Item
        ): Boolean {
            return oldItem.profile?.id == newItem.profile?.id
        }

        override fun areContentsTheSame(
            oldItem: UsersSearch.Item,
            newItem: UsersSearch.Item
        ): Boolean {
            return oldItem == newItem
        }
    }
}