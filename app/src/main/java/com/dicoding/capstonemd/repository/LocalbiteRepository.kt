package com.dicoding.capstonemd.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.dicoding.capstonemd.data.remote.response.RegisterResponse
import com.dicoding.capstonemd.data.remote.retrofit.ApiService
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.data.api.Restaurant
import com.dicoding.capstonemd.data.api.RestaurantDao
import com.dicoding.capstonemd.data.local.fake.Fake
import com.dicoding.capstonemd.data.local.fake.FakeData
import com.dicoding.capstonemd.data.remote.response.LoginResponse
import com.dicoding.capstonemd.data.remote.response.RestaurantsItem
import com.dicoding.capstonemd.data.remote.response.VerifyResponse
import com.dicoding.capstonemd.pref.UserModel
import kotlinx.coroutines.flow.map
import java.lang.Exception

class LocalbiteRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val restaurantDao: RestaurantDao
) {

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val requestBody = apiService.register(name, email, password)
            emit(Result.Success(requestBody))

        } catch (e: Exception) {
            Log.d("UserRepository", "register:${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun verify(email: String, code: String): LiveData<Result<VerifyResponse>> = liveData {
        emit(Result.Loading)
        try {
            val requestBody = apiService.verify(email, code)
            emit(Result.Success(requestBody))

        } catch (e: Exception) {
            Log.d("UserRepository", "register:${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val requestBody = apiService.login(email, password)
            val accessToken = requestBody.accessToken
            val refreshToken = requestBody.refreshToken

            val userModel = UserModel(email, accessToken, refreshToken, true)
            userPreference.saveSession(userModel)

            emit(Result.Success(requestBody))
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

    //getting restaurant data

    suspend fun getRestaurantData(menu: String, latitude: String, longitude: String, radius: Int): List<Restaurant> {
        try {
            val requestBody = apiService.getRestaurant(menu, latitude, longitude, radius)
            val restaurants = requestBody.restaurants
            val fakeData = FakeData.getFakeData()
            val mappedRestaurants = restaurants?.let { mapResponseToEntity(it, fakeData) }
            mappedRestaurants?.let { restaurantDao.insertRestaurants(it) }
            return mappedRestaurants!!
        } catch (e: Exception) {
            // Handle the exception or throw it if needed
            throw e
        }
    }

    //map the response
    private fun mapResponseToEntity (restaurants: List<RestaurantsItem?>, fakeData: List<Fake>): List<Restaurant> {
        val mappedRestaurants = mutableListOf<Restaurant>()
        for (item in restaurants) {
            item?.let {
                val ratingValue = item.rating?.toString()?.toFloatOrNull() ?: 0F

                mappedRestaurants.add(
                    Restaurant(
                        name = item.name ?: "",
                        rating = ratingValue,
                        vicinity = item.vicinity ?: "",
                        hiddenGem = item.hiddenGem ?: false,
                        photoReference = item.photos?.get(0)?.photoReference ?: "",
                        foodCategory = fakeData.find {it.name == item.name}?.name ?: "Pasta",
                    )
                )
            }
        }
        return mappedRestaurants
    }

    fun getAllRestaurant(): LiveData<List<Restaurant>> {
        return restaurantDao.getAllRestaurant()
    }

    suspend fun getRestaurantByCategory(category: String): List<Restaurant> {
        return restaurantDao.getRestaurantsByCategory(category)
    }

    fun getHiddenGems(): LiveData<List<Restaurant>> {
        return restaurantDao.getHiddenGems()
    }

    companion object {
        @Volatile
        private var instance: LocalbiteRepository? = null
        fun getInstance(
            apiService: ApiService, pref: UserPreference, dao: RestaurantDao
        ): LocalbiteRepository = instance ?: synchronized(this) {
            instance ?: LocalbiteRepository(apiService, pref, dao)
        }.also { instance = it }
    }
}