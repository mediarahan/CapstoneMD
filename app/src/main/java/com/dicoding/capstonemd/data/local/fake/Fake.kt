package com.dicoding.capstonemd.data.local.fake

data class Fake(
    val id: Int,
    val name: String,
    val desc: String,
    val avatarUrl: Int,
    val nutrition: List<Nutrition>,
)
