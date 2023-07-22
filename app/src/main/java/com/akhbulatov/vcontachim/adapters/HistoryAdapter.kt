package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.databinding.ItemHistoryBinding
import com.akhbulatov.vcontachim.model.HistoryUser

class HistoryAdapter(val user:ClearListener) :
    ListAdapter<HistoryUser, HistoryAdapter.HistoryViewHolder>(HistoryDuffCallback) {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemHistoryBinding = ItemHistoryBinding.bind(itemView)
    }

    object HistoryDuffCallback : DiffUtil.ItemCallback<HistoryUser>() {

        override fun areItemsTheSame(oldItem: HistoryUser, newItem: HistoryUser): Boolean {
            return oldItem.nameSurname == newItem.nameSurname
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: HistoryUser, newItem: HistoryUser): Boolean {
            return oldItem == newItem
        }
    }

    interface ClearListener{
        fun clearUser(user:HistoryUser)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)

        return HistoryViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history:HistoryUser = getItem(position)

        holder.binding.nameSurname.text = history.nameSurname
        holder.binding.clearUser.setOnClickListener {
            this.user.clearUser(history)
        }
    }
}