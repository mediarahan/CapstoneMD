package com.dicoding.capstonemd.data.api

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null
        @JvmStatic
        fun getInstance(context: Context): RestaurantDatabase {
            return if (INSTANCE != null) {
                INSTANCE as RestaurantDatabase
            } else {
                val instance: RestaurantDatabase =
                    synchronized(RestaurantDatabase::class.java) {
                        androidx.room.Room.databaseBuilder(
                            context.applicationContext,
                            RestaurantDatabase::class.java,
                            "restaurant"
                        )
                            .build()
                    }
                INSTANCE = instance
                instance
            }
        }
    }
}