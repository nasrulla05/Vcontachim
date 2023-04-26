package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemCommunityBinding
import com.akhbulatov.vcontachim.model.Community
import com.bumptech.glide.Glide

class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {
    var communities: List<Community.Item> = emptyList()

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemCommunityBinding = ItemCommunityBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(
            R.layout.item_community,
            parent,
            false
        )
        return CommunityViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val community: Community.Item = communities[position]

        Glide.with(holder.itemView)
            .load(community.avatar)
            .into(holder.binding.communityPhoto)

        holder.binding.communityName.text = community.name
        holder.binding.member.text = "${community.members} участников"
        if (community.verified == "1") {
            holder.binding.verified.visibility = View.VISIBLE
        } else {
            holder.binding.verified.visibility = View.GONE
        }

    }

    override fun getItemCount() = communities.size
}