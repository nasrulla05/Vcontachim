package com.akhbulatov.vcontachim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.vcontachim.R
import com.akhbulatov.vcontachim.database.SearchHistoryModel
import com.akhbulatov.vcontachim.databinding.ItemHistoryBinding

class HistoryAdapter(private val userListener:ClearListener) :
    ListAdapter<SearchHistoryModel, HistoryAdapter.HistoryViewHolder>(HistoryDuffCallback) {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemHistoryBinding = ItemHistoryBinding.bind(itemView)
    }

    object HistoryDuffCallback : DiffUtil.ItemCallback<SearchHistoryModel>() {

        override fun areItemsTheSame(oldItem: SearchHistoryModel, newItem: SearchHistoryModel): Boolean {
            return oldItem.name == newItem.name
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: SearchHistoryModel, newItem: SearchHistoryModel): Boolean {
            return oldItem == newItem
        }
    }

    interface ClearListener{
        fun clearUser(user: SearchHistoryModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)

        return HistoryViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history: SearchHistoryModel = getItem(position)

        holder.binding.nameSurname.text = history.name
        holder.binding.clearUser.setOnClickListener {
            this.userListener.clearUser(history)
        }
    }
}