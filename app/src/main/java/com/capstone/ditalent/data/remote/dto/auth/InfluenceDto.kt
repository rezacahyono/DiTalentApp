package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class InfluenceDto(

	@SerializedName("updated_at")
	val updatedAt: String,

	@SerializedName("name")
	val name: String,

	@SerializedName("created_at")
	val createdAt: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("talent_id")
	val talentId: Int
)