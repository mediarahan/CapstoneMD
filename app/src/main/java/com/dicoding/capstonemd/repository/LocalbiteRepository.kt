package com.dicoding.capstonemd.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.dicoding.capstonemd.data.remote.response.RegisterResponse
import com.dicoding.capstonemd.data.remote.retrofit.ApiService
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.data.remote.response.LoginResponse
import com.dicoding.capstonemd.data.remote.response.VerifyResponse
import com.dicoding.capstonemd.pref.UserModel
import kotlinx.coroutines.flow.map
import java.lang.Exception

class LocalbiteRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val responseBody = apiService.register(name, email, password)
            emit(Result.Success(responseBody))

        } catch (e: Exception) {
            Log.d("UserRepository", "register:${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun verify(email: String, code: String): LiveData<Result<VerifyResponse>> = liveData {
        emit(Result.Loading)
        try {
            val responseBody = apiService.verify(email, code)
            emit(Result.Success(responseBody))

        } catch (e: Exception) {
            Log.d("UserRepository", "register:${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val responseBody = apiService.login(email, password)
            val accessToken = responseBody.accessToken
            val refreshToken = responseBody.refreshToken

            val userModel = UserModel(email, accessToken, refreshToken, true)
            userPreference.saveSession(userModel)

            emit(Result.Success(responseBody))
        } catch (e:Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun observeUserLoginStatus(): LiveData<Boolean?> {
        return userPreference.getSession().map { userModel ->
            userModel.isLogin
        }.asLiveData()
    }

    companion object {
        @Volatile
        private var instance: LocalbiteRepository? = null
        fun getInstance(
            apiService: ApiService, pref: UserPreference
        ): LocalbiteRepository = instance ?: synchronized(this) {
            instance ?: LocalbiteRepository(apiService, pref)
        }.also { instance = it }
    }
}