package com.dicoding.capstonemd.ui.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstonemd.Result
import com.dicoding.capstonemd.adapter.RecommendationAdapter
import com.dicoding.capstonemd.databinding.FragmentHomepageBinding
import com.dicoding.capstonemd.factory.ViewModelFactory
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore

class HomepageFragment : Fragment() {

    private lateinit var binding: FragmentHomepageBinding
    private lateinit var userPreference: UserPreference

    private val homepageViewModel by viewModels<HomepageViewModel> {
        ViewModelFactory.getInstance(
            requireContext().applicationContext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homepageViewModel.fetchRecommendationData()

        userPreference = UserPreference.getInstance(requireContext().dataStore)
        // Observe changes to user email and update the TextView
        lifecycleScope.launchWhenStarted {
            userPreference.getUserDisplayName().collect { userName ->
                // Update the TextView with the retrieved username
                binding.homepageUserName.text = userName
            }
        }

        //rv adapter stuff
        val recommendationAdapter = RecommendationAdapter()
        binding.rvFoodRecommendation.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFoodRecommendation.adapter = recommendationAdapter

        homepageViewModel.fetchAllRecommendation()

        homepageViewModel.recommendationData.observe(viewLifecycleOwner) {result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    // Update the RecyclerView adapter with the new data
                    recommendationAdapter.submitList(result.data)
                    showLoading(false)
                }

                is Result.Error -> {
                    // Handle error state if needed
                    showToast("ERROR STATE: ${result.data}")
                    Log.e("TabMapsFragment", "Error: ${result.data}")
                    showLoading(false)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}