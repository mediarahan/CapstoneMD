package com.dicoding.capstonemd.data.remote.retrofit

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object ApiConfig {
    fun getApiService(context: Context): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val accessToken = runBlocking {
                UserPreference.getInstance(context.dataStore).getSession().first().accessToken
            }

            val originalRequest: Request = chain.request()

            // Modify the request to include the "Authorization" header
            val requestWithAuthorization: Request = originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

            chain.proceed(requestWithAuthorization)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor) // Add the authInterceptor here
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://localbitebackenduntuktestingmd2.safira1003.repl.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
