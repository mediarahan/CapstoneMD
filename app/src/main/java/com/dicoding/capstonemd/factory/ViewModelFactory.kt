package com.dicoding.capstonemd.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstonemd.di.Injection
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore
import com.dicoding.capstonemd.repository.LocalbiteRepository
import com.dicoding.capstonemd.ui.detail.TabMapsViewModel
import com.dicoding.capstonemd.ui.login.LoginViewModel
import com.dicoding.capstonemd.ui.main.MainViewModel
import com.dicoding.capstonemd.ui.question.QuestionViewModel
import com.dicoding.capstonemd.ui.recommend.RecommendationViewModel
import com.dicoding.capstonemd.ui.register.RegisterViewModel
import com.dicoding.capstonemd.ui.settings.SettingsViewModel
import com.dicoding.capstonemd.ui.verify.VerifyViewModel

class ViewModelFactory(private val repository: LocalbiteRepository, private val pref: UserPreference) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }
            modelClass.isAssignableFrom(VerifyViewModel::class.java) -> {
                VerifyViewModel(repository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(TabMapsViewModel::class.java) -> {
                TabMapsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }
            modelClass.isAssignableFrom(QuestionViewModel::class.java) -> {
                QuestionViewModel(repository) as T
            }
            modelClass.isAssignableFrom(RecommendationViewModel::class.java) -> {
                RecommendationViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(Injection.provideRepository(context), UserPreference.getInstance(context.dataStore))
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}