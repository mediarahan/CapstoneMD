package com.dicoding.capstonemd.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.capstonemd.data.local.fake.FakeNutritionData
import com.dicoding.capstonemd.databinding.FragmentTabNutritionBinding

class TabNutritionFragment : Fragment() {

    private var _binding: FragmentTabNutritionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabNutritionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nutritionData = FakeNutritionData.getFakeNutritionData()

        binding.detailCarbText.text = "CARBS: ${nutritionData[0].carb}"
        binding.detailProteinText.text = "PROTEIN: ${nutritionData[0].protein}"
        binding.detailFatText.text = "FAT: ${nutritionData[0].fat}"
        binding.detailVitaminText.text = "VITAMIN: ${nutritionData[0].vitamins}"
        binding.detailMineralText.text = "MINERAL: ${nutritionData[0].minerals}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
