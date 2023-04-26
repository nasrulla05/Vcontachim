package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemFriendBinding
import com.akhbulatov.vcontachim.model.Friends
import com.bumptech.glide.Glide

class FriendsAdapter : RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>() {
    var friends: List<Friends.Item> = emptyList()

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
        val friend: Friends.Item = friends[position]

        Glide.with(holder.itemView)
            .load(friend.avatar100)
            .into(holder.binding.avatar48)

        holder.binding.nameLastName.text =
            "${friend.firstName} ${friend.lastName}"
    }

    override fun getItemCount() = friends.size
}