package com.capstone.ditalent.data

import com.capstone.ditalent.data.remote.dto.auth.ResponseUser
import com.capstone.ditalent.model.Role
import com.capstone.ditalent.model.User

fun clearUser() = User(
    token = "",
    name = "",
    email = "",
    role = Role.UMKM,
    noPhone = "",
    avatar = "",
    address = "",
    isLogin = false
)

fun ResponseUser.toUser(): User {
    return User(
        token = this.accessToken,
        name = this.data.name,
        email = this.data.email,
        role = if (this.data.role == Role.UMKM.toString()) Role.UMKM else Role.TALENT,
        noPhone = this.data.noPhone,
        avatar = "",
        address = "",
    )
}