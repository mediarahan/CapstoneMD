package com.dicoding.capstonemd.ui.verify

import androidx.lifecycle.ViewModel
import com.dicoding.capstonemd.repository.LocalbiteRepository

class VerifyViewModel (private val repository: LocalbiteRepository): ViewModel() {
    fun verify (email: String, code: String) = repository.verify(email,code)
}