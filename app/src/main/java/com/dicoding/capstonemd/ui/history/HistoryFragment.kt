package com.dicoding.capstonemd.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstonemd.adapter.FakeAdapter
import com.dicoding.capstonemd.adapter.FakeAdapter2
import com.dicoding.capstonemd.data.Fake
import com.dicoding.capstonemd.data.FakeData
import com.dicoding.capstonemd.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fakeDataList = FakeData.getFakeData()
        setHomepageData(fakeDataList)
    }

    private fun setHomepageData(detailedItemsData: List<Fake>) {
        val adapter = FakeAdapter2()
        adapter.submitList(detailedItemsData)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHistory.layoutManager = layoutManager
        binding.rvHistory.adapter = adapter
    }

}