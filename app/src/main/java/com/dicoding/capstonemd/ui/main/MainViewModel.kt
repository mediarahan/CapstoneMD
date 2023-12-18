package com.dicoding.capstonemd.ui.main

import androidx.lifecycle.ViewModel
import com.dicoding.capstonemd.repository.LocalbiteRepository

class MainViewModel (private val repository: LocalbiteRepository): ViewModel() {
    suspend fun logout() {
        repository.logout()
    }
}