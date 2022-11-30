package com.capstone.ditalent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.ditalent.databinding.ItemRewardBinding
import com.capstone.ditalent.utils.Reward

class ListRewardAdapter(
    private val onClickItem: () -> Unit
) : ListAdapter<Reward, ListRewardAdapter.ListRewardViewHolder>(DiffCallback) {
    private lateinit var context: Context

    inner class ListRewardViewHolder(
        private val binding: ItemRewardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(reward: Reward) {
            binding.apply {
                ivBannerReward.setImageResource(reward.image)
                tvTitleReward.text = context.getString(reward.title)
                root.setOnClickListener {
                    onClickItem()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRewardViewHolder {
        context = parent.context
        val binding = ItemRewardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListRewardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListRewardViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<Reward>() {
        override fun areItemsTheSame(oldItem: Reward, newItem: Reward): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Reward, newItem: Reward): Boolean {
            return oldItem == newItem
        }

    }
}