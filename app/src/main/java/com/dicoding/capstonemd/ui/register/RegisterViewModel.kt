package com.dicoding.capstonemd.ui.register

import androidx.lifecycle.ViewModel
import com.dicoding.capstonemd.repository.LocalbiteRepository

class RegisterViewModel (private val repository: LocalbiteRepository): ViewModel() {
    fun register(name: String, email: String, password: String) = repository.register(name, email, password)
}