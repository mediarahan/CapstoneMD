package com.dicoding.capstonemd.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstonemd.adapter.FakeAdapter
import com.dicoding.capstonemd.data.local.fake.Fake
import com.dicoding.capstonemd.data.local.fake.FakeData
import com.dicoding.capstonemd.databinding.FragmentHomepageBinding
import com.dicoding.capstonemd.pref.UserPreference
import com.dicoding.capstonemd.pref.dataStore

class HomepageFragment : Fragment() {

    private lateinit var binding: FragmentHomepageBinding
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreference = UserPreference.getInstance(requireContext().dataStore)
        // Observe changes to user email and update the TextView
        lifecycleScope.launchWhenStarted {
            userPreference.getUserDisplayName().collect { userName ->
                // Update the TextView with the retrieved username
                binding.homepageUserName.text = userName
            }
        }

        val fakeDataList = FakeData.getFakeData()
        setHomepageData(fakeDataList)
    }

    private fun setHomepageData(detailedItemsData: List<Fake>) {
        val adapter = FakeAdapter()
        adapter.submitList(detailedItemsData)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFoodRecommendation.layoutManager = layoutManager
        binding.rvFoodRecommendation.adapter = adapter
    }
}