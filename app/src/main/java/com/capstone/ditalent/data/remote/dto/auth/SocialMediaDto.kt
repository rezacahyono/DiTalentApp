package com.capstone.ditalent.data.remote.dto.auth

import com.google.gson.annotations.SerializedName

data class SocialMediaDto(

	@SerializedName("insight")
	val insight: String,

	@SerializedName("url_link")
	val urlLink: String,

	@SerializedName("followers")
	val followers: String,

	@SerializedName("updated_at")
	val updatedAt: String,

	@SerializedName("user_id")
	val userId: Int,

	@SerializedName("name")
	val name: String,

	@SerializedName("created_at")
	val createdAt: String,

	@SerializedName("id")
	val id: Int,

	@SerializedName("username")
	val username: String
)