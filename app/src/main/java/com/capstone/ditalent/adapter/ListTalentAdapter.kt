package com.capstone.ditalent.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.capstone.ditalent.databinding.ItemRowTalentBinding
import com.capstone.ditalent.model.Talent
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.Constant
import com.capstone.ditalent.utils.Utilities.getInitialName

class ListTalentAdapter(
    private val onClickItem: (String) -> Unit
) : ListAdapter<Pair<User, Talent>, ListTalentAdapter.ListTalentViewHolder>(DiffCallback) {

    inner class ListTalentViewHolder(
        private val binding: ItemRowTalentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(talent: Pair<User, Talent>) {
            binding.apply {

                val photo = talent.first.photo
                if (photo.isNullOrBlank() || photo == Constant.NULL) {
                    ivAvatar.avatarInitials = talent.first.name?.getInitialName()
                } else {
                    ivAvatar.load(photo)
                }

                tvName.text = talent.first.name
                ratingBar.rating = 4.4F
                tvRate.text = talent.second.rate.toString()
                tvCatInfluences.text = "Fashion | Food"

                root.setOnClickListener {
                    talent.first.name?.let {
                        onClickItem(it)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTalentViewHolder {
        val binding =
            ItemRowTalentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListTalentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListTalentViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<Pair<User, Talent>>() {
        override fun areItemsTheSame(
            oldItem: Pair<User, Talent>,
            newItem: Pair<User, Talent>
        ): Boolean {
            return oldItem.first == newItem.first && oldItem.second == newItem.second
        }

        override fun areContentsTheSame(
            oldItem: Pair<User, Talent>,
            newItem: Pair<User, Talent>
        ): Boolean {
            return oldItem.first.name == newItem.first.name && oldItem.second == newItem.second
        }

    }
}