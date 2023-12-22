package com.dicoding.capstonemd.data.remote.retrofit

import com.dicoding.capstonemd.data.remote.response.LoginResponse
import com.dicoding.capstonemd.data.remote.response.RecommendationResponse
import com.dicoding.capstonemd.data.remote.response.RegisterResponse
import com.dicoding.capstonemd.data.remote.response.RestaurantResponse
import com.dicoding.capstonemd.data.remote.response.UserPrefResponse
import com.dicoding.capstonemd.data.remote.response.VerifyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @FormUrlEncoded
    @POST("auth/signup")
    suspend fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("auth/verify")
    suspend fun verify(
        @Field("email") email: String,
        @Field("code") code: String
    ): VerifyResponse

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("maps/nearest-restaurant")
    suspend fun getRestaurant(
        @Field("menu") menu: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("radius") radius: Int
    ): RestaurantResponse

    data class UserPreferences(
        val activity: String,
        val age: Int,
        val allergen: List<String>,
        val disease: String,
        val gender: String,
        val halal: Boolean,
        val height: Int,
        val number_of_meals: Int,
        val weight: Int,
        val weight_loss: String,
    )

    @PUT("user/change-preferences")
    suspend fun putUserPreference(@Body requestBody: Map<String, UserPreferences>
    ): UserPrefResponse

    @GET("food/get-meal-plan")
    suspend fun getMealRecommendations(): RecommendationResponse
    
}