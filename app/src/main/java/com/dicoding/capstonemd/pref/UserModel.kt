package com.dicoding.capstonemd.pref

data class UserModel(
    val email: String?,
    val accessToken: String?,
    val refreshToken: String?,
    val isLogin: Boolean? = false
)