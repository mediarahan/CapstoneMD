package com.dicoding.capstonemd.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.repository.LocalbiteRepository
import com.dicoding.capstonemd.data.api.Restaurant
import kotlinx.coroutines.launch


class TabMapsViewModel(private val repository: LocalbiteRepository) : ViewModel() {
    private val _restaurantData = MutableLiveData<Result<List<Restaurant>>>()
    val restaurantData: LiveData<Result<List<Restaurant>>> = _restaurantData

    fun fetchRestaurantData(menu: String, latitude: String, longitude: String, radius: Int) {
        viewModelScope.launch {
            _restaurantData.value = Result.Loading
            try {
                val response = repository.getRestaurantData(menu, latitude, longitude, radius)
                _restaurantData.value = Result.Success(response)
            } catch (e: Exception) {
                _restaurantData.value = Result.Error(e.message.toString())
            }
        }
    }

    fun getAllRestaurant(): LiveData<List<Restaurant>> {
        return repository.getAllRestaurant()
    }

    fun fetchRestaurantsByCategory(category: String) {
        viewModelScope.launch {
            _restaurantData.value = Result.Loading
            try {
                val response = repository.getRestaurantByCategory(category)
                _restaurantData.value = Result.Success(response)
            } catch (e: Exception) {
                _restaurantData.value = Result.Error(e.message.toString())
            }
        }
    }
}
