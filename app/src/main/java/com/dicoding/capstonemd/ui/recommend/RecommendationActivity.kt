package com.dicoding.capstonemd.ui.recommend

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.adapter.RecommendationAdapter
import com.dicoding.capstonemd.databinding.ActivityRecommendationBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore

class RecommendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecommendationBinding
    private lateinit var userPreference: UserPreference

    private val recommendationViewModel by viewModels<RecommendationViewModel> {
        ViewModelFactory.getInstance(
            applicationContext
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecommendationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        recommendationViewModel.fetchRecommendationData()
        userPreference = UserPreference.getInstance(dataStore)

        lifecycleScope.launchWhenStarted {
            userPreference.getUserDisplayName().collect { userName ->
                binding.recommendationUserName.text = userName
            }
        }

        // RecyclerView adapter setup
        val recommendationAdapter = RecommendationAdapter()
        binding.rvFoodRecommendation.layoutManager = LinearLayoutManager(this)
        binding.rvFoodRecommendation.adapter = recommendationAdapter

        recommendationViewModel.fetchAllRecommendation()

        recommendationViewModel.recommendationData.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    recommendationAdapter.submitList(result.data)
                    showLoading(false)
                }

                is Result.Error -> {
                    showToast("ERROR STATE: ${result.data}")
                    Log.e("RecommendationActivity", "Error: ${result.data}")
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
