package com.capstone.ditalent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.ditalent.databinding.ContainerItemBoardingBinding
import com.capstone.ditalent.domain.model.Boarding
import com.capstone.ditalent.utils.Utilities.fromHtml

class BoardingAdapter : ListAdapter<Boarding, BoardingAdapter.BoardingViewHolder>(DiffCallback) {
    private lateinit var context: Context

    inner class BoardingViewHolder(
        private val binding: ContainerItemBoardingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(boarding: Boarding) {
            binding.apply {
                ivImageBoarding.setImageResource(boarding.image)
                tvTitleBoarding.text = fromHtml(
                    context.getString(boarding.title)
                )
                tvDescriptionBoarding.text = context.getString(boarding.description)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardingViewHolder {
        context = parent.context
        val binding =
            ContainerItemBoardingBinding.inflate(LayoutInflater.from(context), parent, false)
        return BoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BoardingViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<Boarding>() {
        override fun areItemsTheSame(oldItem: Boarding, newItem: Boarding): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Boarding, newItem: Boarding): Boolean {
            return oldItem.title == newItem.title
        }
    }
}