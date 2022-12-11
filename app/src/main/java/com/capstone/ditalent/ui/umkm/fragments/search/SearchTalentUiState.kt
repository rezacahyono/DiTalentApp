package com.capstone.ditalent.ui.umkm.fragments.search

import com.capstone.ditalent.model.User

data class SearchTalentUiState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val talents: List<User> = emptyList()
)

data class Filter(
    var sort: Sort? = null,
    var rateStart: Int = 0,
    var rateEnt: Int = 0,
    var followerStart: Int = 0,
    var followerEnd: Int = 0,
    var gender: String = ""
)

enum class Sort {
    RATE_HIGH,
    RATE_LOW,
    NOW,
    FOLLOWERS_HIGH,
    FOLLOWERS_LOW,
}
