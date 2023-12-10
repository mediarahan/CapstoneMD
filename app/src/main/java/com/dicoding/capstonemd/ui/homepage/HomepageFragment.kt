package com.dicoding.capstonemd.ui.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstonemd.adapter.FakeAdapter
import com.dicoding.capstonemd.data.local.fake.Fake
import com.dicoding.capstonemd.data.local.fake.FakeData
import com.dicoding.capstonemd.databinding.FragmentHomepageBinding

class HomepageFragment : Fragment() {

    private lateinit var binding: FragmentHomepageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomepageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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