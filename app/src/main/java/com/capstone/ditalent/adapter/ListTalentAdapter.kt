package com.capstone.ditalent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.capstone.ditalent.databinding.ItemRowTalentBinding
import com.capstone.ditalent.model.User
import com.capstone.ditalent.utils.Constant
import com.capstone.ditalent.utils.Utilities.cleanListInfluence
import com.capstone.ditalent.utils.Utilities.dpToPx
import com.capstone.ditalent.utils.Utilities.getFormattedCurrency
import com.capstone.ditalent.utils.Utilities.getInitialName
import com.capstone.ditalent.utils.Utilities.randomColor

class ListTalentAdapter(
    private val onClickItem: (String) -> Unit
) : ListAdapter<User, ListTalentAdapter.ListTalentViewHolder>(DiffCallback) {
    private lateinit var ctx: Context

    inner class ListTalentViewHolder(
        private val binding: ItemRowTalentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.apply {

                val photo = user.photo
                if (photo.isNullOrBlank() || photo == Constant.NULL) {
                    ivAvatar.apply {
                        avatarInitials = user.name?.getInitialName()
                        avatarInitialsBackgroundColor = randomColor(ctx)
                        avatarInitialsTextSize = ctx.dpToPx(80F)
                    }
                } else {
                    ivAvatar.load(photo)
                }

                tvName.text = user.name
                ratingBar.rating = 4.4F
                val talent = user.talent
                if (talent != null) {
                    tvRate.text = getFormattedCurrency(talent.rate)
                    tvCatInfluences.apply {
                        isVisible = talent.influence.isNotEmpty()
                        text = talent.influence.toString().cleanListInfluence()
                    }
                }

                root.setOnClickListener {
                    user.name?.let {
                        onClickItem(it)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTalentViewHolder {
        ctx = parent.context
        val binding =
            ItemRowTalentBinding.inflate(LayoutInflater.from(ctx), parent, false)
        return ListTalentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListTalentViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: User,
            newItem: User
        ): Boolean {
            return oldItem.name == newItem.name
        }

    }
}