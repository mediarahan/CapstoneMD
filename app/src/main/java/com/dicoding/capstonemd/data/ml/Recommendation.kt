package com.dicoding.capstonemd.data.ml

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "recommendation")
@Parcelize
class Recommendation(
    @PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    val id: Int = 0,

    @field:ColumnInfo(name = "name")
    val name: String?,

    @field:ColumnInfo(name = "mealTime")
    val mealTime: String?,

    @field:ColumnInfo(name = "calories")
    val calories: Double?,

    @field:ColumnInfo(name = "protein")
    val proteinContent: Double?,

    @field:ColumnInfo(name = "carbs")
    val carbohydrateContent: Double?,

    @field:ColumnInfo(name = "fat")
    val fatContent: Double?,

    @field:ColumnInfo(name = "images")
    val images: String?,
) : Parcelable