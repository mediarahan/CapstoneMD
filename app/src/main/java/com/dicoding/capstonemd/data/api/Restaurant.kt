package com.dicoding.capstonemd.data.api

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "restaurants")
@Parcelize
data class Restaurant (
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int = 0,

    @field:ColumnInfo(name = "name")
    val name: String,

    @field:ColumnInfo(name = "rating")
    val rating: Float = 0F,

    @field:ColumnInfo(name = "vicinity")
    val vicinity: String,

    @field:ColumnInfo(name = "hidden_gem")
    val hiddenGem: Boolean = false,

    @field:ColumnInfo(name = "photo_reference")
    val photoReference: String,

    @field:ColumnInfo(name = "food_category")
    val foodCategory: String,
): Parcelable