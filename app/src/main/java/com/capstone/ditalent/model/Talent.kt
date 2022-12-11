package com.capstone.ditalent.model


data class Talent(
    val age: String? = null,
    val region: String? = null,
    val gender: String? = null,
    val rate: Int = 0,
    val influence: List<String> = emptyList()
)
