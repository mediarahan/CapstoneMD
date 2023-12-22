package com.dicoding.capstonemd.data.remote.response

import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

    @field:SerializedName("recommendationResponse")
    val recommendationResponse: List<RecommendationsItem?>? = null
)

data class RecommendationsItem(

    @field:SerializedName("CookTime")
    val cookTime: String? = null,

    @field:SerializedName("Keywords")
    val keywords: List<String?>? = null,

    @field:SerializedName("RecipeInstructions")
    val recipeInstructions: List<String?>? = null,

    @field:SerializedName("Images")
    val images: String? = null,

    @field:SerializedName("RecipeId")
    val recipeId: Int? = null,

    @field:SerializedName("RecipeIngredientParts")
    val recipeIngredientParts: List<String?>? = null,

    @field:SerializedName("SodiumContent")
    val sodiumContent: Any? = null,

    @field:SerializedName("SugarContent")
    val sugarContent: Any? = null,

    @field:SerializedName("Calories")
    val calories: Any? = null,

    @field:SerializedName("CholesterolContent")
    val cholesterolContent: Any? = null,

    @field:SerializedName("Name")
    val name: String? = null,

    @field:SerializedName("PrepTime")
    val prepTime: String? = null,

    @field:SerializedName("RecipeCategory")
    val recipeCategory: String? = null,

    @field:SerializedName("TotalTime")
    val totalTime: String? = null,

    @field:SerializedName("SaturatedFatContent")
    val saturatedFatContent: Any? = null,

    @field:SerializedName("CarbohydrateContent")
    val carbohydrateContent: Any? = null,

    @field:SerializedName("FatContent")
    val fatContent: Any? = null,

    @field:SerializedName("ProteinContent")
    val proteinContent: Any? = null,

    @field:SerializedName("FiberContent")
    val fiberContent: Any? = null,

    @field:SerializedName("MealTime")
    val mealTime: String? = null
)