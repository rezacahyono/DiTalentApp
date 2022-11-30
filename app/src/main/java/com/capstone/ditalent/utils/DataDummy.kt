package com.capstone.ditalent.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.capstone.ditalent.R

object DataDummy {

    val listReward: List<Reward> = listOf(
        Reward(R.drawable.cat,R.string.content_desc_confirmation),
        Reward(R.drawable.cat,R.string.content_desc_confirmation),
        Reward(R.drawable.cat,R.string.content_desc_confirmation),
        Reward(R.drawable.cat,R.string.content_desc_confirmation),
        Reward(R.drawable.cat,R.string.content_desc_confirmation),
    )

}

data class Reward(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int
)