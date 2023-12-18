package com.dicoding.capstonemd.di

import android.content.Context
import com.dicoding.capstonemd.data.remote.retrofit.ApiConfig
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore
import com.dicoding.capstonemd.repository.LocalbiteRepository

object Injection {
    fun provideRepository(context: Context): LocalbiteRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return LocalbiteRepository.getInstance(apiService, pref)
    }
}