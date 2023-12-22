package com.dicoding.capstonemd.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

    @field:SerializedName("recommendations")
    val recommendations: List<RecommendationsItem?>? = null
)

data class RecommendationsItem(

    @field:SerializedName("CookTime")
    val cookTime: String? = null,

    @field:SerializedName("Images")
    val images: String? = null,

    @field:SerializedName("TotalTime")
    val totalTime: String? = null,

    @field:SerializedName("CarbohydrateContent")
    val carbohydrateContent: Any? = null,

    @field:SerializedName("FatContent")
    val fatContent: Any? = null,

    @field:SerializedName("ProteinContent")
    val proteinContent: Any? = null,

    @field:SerializedName("Calories")
    val calories: Any? = null,

    @field:SerializedName("CholesterolContent")
    val cholesterolContent: Any? = null,

    @field:SerializedName("FiberContent")
    val fiberContent: Float? = null,

    @field:SerializedName("MealTime")
    val mealTime: String? = null,

    @field:SerializedName("Name")
    val name: String? = null
)
