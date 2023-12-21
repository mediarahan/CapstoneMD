package com.dicoding.capstonemd.di

import android.content.Context
import com.dicoding.capstonemd.data.api.RestaurantDao
import com.dicoding.capstonemd.data.api.RestaurantDatabase
import com.dicoding.capstonemd.data.remote.retrofit.ApiConfig
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore
import com.dicoding.capstonemd.repository.LocalbiteRepository

object Injection {

    fun provideRepository(context: Context): LocalbiteRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(context)
        val restaurantDao = provideDao(context) // Get the DAO
        return LocalbiteRepository.getInstance(apiService, pref, restaurantDao)
    }

    fun provideDao(context: Context): RestaurantDao {
        val db = RestaurantDatabase.getInstance(context)
        return db.restaurantDao()
    }
}