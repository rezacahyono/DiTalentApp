package com.capstone.ditalent.model


import java.sql.Timestamp


data class User(
    val id: String? = null,
    val email: String? = null,
    val photo: String? = null,
    val name: String? = null,
    val noPhone: String? = null,
    val role: String? = null,
    var talent: Talent? = null,
    var umkm: Umkm? = null,
    val createdAt: String = Timestamp(System.currentTimeMillis()).toString()
)
