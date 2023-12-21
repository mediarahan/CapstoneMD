package com.dicoding.capstonemd.data.api

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRestaurants(restaurants: List<Restaurant>)

    @Query("SELECT * FROM restaurants")
    fun getAllRestaurant(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurants WHERE food_category = :category")
    suspend fun getRestaurantsByCategory(category: String): List<Restaurant>

    @Query("DELETE FROM restaurants")
    suspend fun deleteAllRestaurants()

    @Query("SELECT COUNT(*) FROM restaurants WHERE food_category = :category")
    suspend fun countRestaurantsByCategory(category: String): Int

    @Query("SELECT * FROM restaurants WHERE hidden_gem = 1 AND food_category = :category")
    suspend fun getHiddenGemRestaurantsByCategory(category: String): List<Restaurant>

}