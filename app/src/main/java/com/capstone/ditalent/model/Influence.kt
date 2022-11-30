package com.capstone.ditalent.model

import kotlinx.serialization.Serializable

@Serializable
data class Influence(
    val id: Int,
    val talentId: Int,
    val name: String
)
