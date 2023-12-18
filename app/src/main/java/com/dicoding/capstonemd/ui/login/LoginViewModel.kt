package com.dicoding.capstonemd.ui.login

import androidx.lifecycle.ViewModel
import com.dicoding.capstonemd.repository.LocalbiteRepository

class LoginViewModel (private val repository: LocalbiteRepository): ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
}