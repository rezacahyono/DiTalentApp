package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class TalentDto(

    @SerializedName("full_name")
    val fullName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("user_id")
    val userId: Int? = null,

    @SerializedName("rate")
    val rate: Int? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("id")
    val id: Int,

    @SerializedName("region")
    val region: String? = null,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("age")
    val age: String? = null,

    @SerializedName("influence")
    val influenceDto: List<InfluenceDto> = emptyList()

)