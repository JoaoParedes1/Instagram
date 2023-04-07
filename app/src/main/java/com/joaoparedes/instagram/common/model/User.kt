package com.joaoparedes.instagram.common.model

data class User(
    val uuid: String? = null,
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val postCount: Int = 0,
    val followingCount: Int = 0,
    val followersCount: Int = 0,
)
