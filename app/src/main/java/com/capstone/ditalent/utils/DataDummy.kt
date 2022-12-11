package com.capstone.ditalent.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.capstone.ditalent.R

object DataDummy {

    val listReward: List<Reward> = listOf(
        Reward(R.drawable.banner_reward_1,R.string.title_reward_1),
        Reward(R.drawable.banner_reward_2,R.string.title_reward_2),
        Reward(R.drawable.banner_reward_3,R.string.title_reward_3)
    )

}

data class Reward(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int
)