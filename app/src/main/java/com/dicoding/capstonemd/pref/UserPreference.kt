package com.dicoding.capstonemd.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email ?: ""
            preferences[A_TOKEN_KEY] = user.accessToken ?: ""
            preferences[R_TOKEN_KEY] = user.refreshToken ?: ""
            preferences[IS_LOGIN_KEY] = true
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel (
                preferences[EMAIL_KEY] ?: "",
                preferences[A_TOKEN_KEY] ?: "",
                preferences[R_TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private object PreferencesKeys {
        val IS_HIDDEN_GEM = booleanPreferencesKey("is_hidden_gem")
    }

    fun isHiddenGem(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[PreferencesKeys.IS_HIDDEN_GEM] ?: false
        }
    }

    suspend fun saveHiddenGemSetting(isHiddenGem: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_HIDDEN_GEM] = isHiddenGem
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val A_TOKEN_KEY = stringPreferencesKey("access_token")
        private val R_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val IS_LOGIN_KEY = booleanPreferencesKey("isLogin")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}