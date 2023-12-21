package com.dicoding.capstonemd.ui.question

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonemd.data.remote.retrofit.ApiService
import com.dicoding.capstonemd.repository.LocalbiteRepository
import kotlinx.coroutines.launch

class QuestionViewModel(private val repository: LocalbiteRepository) : ViewModel() {

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean>
        get() = _updateResult

    // This function triggers the API request
    fun updateUserPreferences(userPreferences: Map<String, ApiService.UserPreferences>) {
        viewModelScope.launch {
            try {
                val response = repository.updateUserPreferences(userPreferences)
                // Handle the response as needed
                _updateResult.value = true
            } catch (e: Exception) {
                // Handle exceptions, such as network errors
                _updateResult.value = false
            }
        }
    }

    // Other ViewModel functions can go here...
}
