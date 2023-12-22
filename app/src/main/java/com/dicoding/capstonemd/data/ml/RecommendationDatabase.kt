package com.dicoding.capstonemd.data.ml

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.dicoding.capstonemd.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.concurrent.Executors

@Database(entities = [Recommendation::class], version = 1, exportSchema = false)
abstract class RecommendationDatabase : RoomDatabase() {
    abstract fun RecommendationDao(): RecommendationDao

    companion object {
        @Volatile
        private var INSTANCE: RecommendationDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): RecommendationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecommendationDatabase::class.java,
                    "recommendation.db"
                )
                    //prepopulate database
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            //new thread
                            Executors.newSingleThreadExecutor().execute {
                                val dao = getInstance(context).RecommendationDao()
                                fillWithStartingData(context, dao)
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }


        private fun fillWithStartingData(context: Context, dao: RecommendationDao) {
            val task = loadJsonArray(context)
            try {
                if (task != null) {
                    for (i in 0 until task.length()) {
                        val item = task.getJSONObject(i)
                        dao.insertAll(
                            Recommendation(
                                item.getInt("id"),
                                item.getString("name"),
                                item.getString("mealTime"),
                                item.getDouble("calories"),
                                item.getDouble("protein"),
                                item.getDouble("carbs"),
                                item.getDouble("fat"),
                                item.getString("images")
                            )
                        )
                    }
                }
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
        }

        private fun loadJsonArray(context: Context): JSONArray? {
            val builder = StringBuilder()
            val `in` = context.resources.openRawResource(R.raw.recommendation)
            val reader = BufferedReader(InputStreamReader(`in`))
            var line: String?
            try {
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val json = JSONObject(builder.toString())
                return json.getJSONArray("recommendation")
            } catch (exception: IOException) {
                exception.printStackTrace()
            } catch (exception: JSONException) {
                exception.printStackTrace()
            }
            return null
        }
    }
}