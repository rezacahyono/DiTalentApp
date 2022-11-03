package com.capstone.ditalent.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Boarding(
    @DrawableRes
    val image: Int,
    @StringRes
    val title: Int,
    @StringRes
    val description: Int
)
