package com.dicoding.capstonemd.data.ml

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecommendationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendations(restaurants: List<Recommendation>)

    @Query("SELECT * FROM recommendation")
    suspend fun getAllRecommendations(): List<Recommendation>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg tasks: Recommendation)
}