package com.capstone.ditalent.data
//
//import com.capstone.ditalent.data.remote.dto.auth.*
//import com.capstone.ditalent.model.*
//
//fun clearUser() = User(
//    token = "",
//    username = "",
//    email = "",
//    role = Role.UMKM,
//    noPhone = "",
//    avatar = "",
//    address = "",
//    isLogin = false
//)
//
//
//fun ResponseUser.toUser(): User {
//    val user = this.userDto
//    return User(
//        token = token ?: "",
//        username = user.username,
//        email = user.email,
//        role = if (user.role == Role.UMKM.toString()) Role.UMKM else Role.TALENT,
//        noPhone = user.noPhone,
//        avatar = user.avatar ?: "",
//        address = user.address ?: "",
//        talent = user.talentDto?.toTalent(),
//        umkm = null,
//        socialMedias = user.socialMediaDto?.map { it.toSocialMedia() } ?: emptyList()
//    )
//}
//
//fun TalentDto.toTalent(): Talent {
//    return Talent(
//        id = id,
//        slug = slug,
//        fullName = fullName ?: "",
//        age = age ?: "",
//        region = region ?: "",
//        gender = gender ?: "",
//        rate = rate ?: 0,
//        influences = influenceDto.map { it.toInfluence() }
//    )
//}
//
//fun SocialMediaDto.toSocialMedia(): SocialMedia {
//    return SocialMedia(
//        id = id,
//        userId = userId,
//        name = name,
//        username = username,
//        followers = followers,
//        insight = insight,
//        url = urlLink
//    )
//}
//
//fun InfluenceDto.toInfluence(): Influence {
//    return Influence(
//        id = id,
//        talentId = talentId,
//        name = name
//    )
//}