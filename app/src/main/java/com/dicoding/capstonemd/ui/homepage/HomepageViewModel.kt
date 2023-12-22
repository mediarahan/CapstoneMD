//package com.dicoding.capstonemd.ui.homepage
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.dicoding.capstonemd.Result
//import com.dicoding.capstonemd.data.ml.Recommendation
//import com.dicoding.capstonemd.repository.LocalbiteRepository
//import kotlinx.coroutines.launch
//
//class HomepageViewModel(private val repository: LocalbiteRepository) : ViewModel() {
//
//    private val _recommendationData = MutableLiveData<Result<List<Recommendation>>>()
//    val recommendationData: LiveData<Result<List<Recommendation>>> = _recommendationData
//
//    fun fetchRecommendationData() {
//        viewModelScope.launch {
//            _recommendationData.value = Result.Loading
//            try {
//                val response = repository.getRecommendationData()
//                _recommendationData.value = Result.Success(response)
//            } catch (e: Exception) {
//                _recommendationData.value = Result.Error(e.message.toString())
//            }
//        }
//    }
//
//    fun fetchAllRecommendation() {
//        viewModelScope.launch {
//            _recommendationData.value = Result.Loading
//            try {
//                val response = repository.getAllRecommendation()
//                _recommendationData.value = Result.Success(response)
//            } catch (e: Exception) {
//                _recommendationData.value = Result.Error(e.message.toString())
//            }
//        }
//    }
//}