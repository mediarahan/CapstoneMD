package com.dicoding.capstonemd.data.remote.retrofit

import com.dicoding.capstonemd.data.remote.response.LoginResponse
import com.dicoding.capstonemd.data.remote.response.RegisterResponse
import com.dicoding.capstonemd.data.remote.response.VerifyResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

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
}