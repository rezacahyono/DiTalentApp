package com.capstone.ditalent.model

import kotlinx.serialization.Serializable

@Serializable
data class Talent(
    val id: Int,
    val slug: String,
    val fullName: String,
    val age: String,
    val region: String,
    val gender: String,
    val rate: Int,
    val influences: List<Influence> = emptyList()
)
