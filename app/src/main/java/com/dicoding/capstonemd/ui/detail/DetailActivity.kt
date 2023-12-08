package com.dicoding.capstonemd.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.adapter.SectionsPagerAdapter

import com.dicoding.capstonemd.data.FakeNutritionData
import com.dicoding.capstonemd.data.Nutrition
import com.dicoding.capstonemd.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val name = intent.getStringExtra("name")
        val avatarUrl = intent.getIntExtra("avatar",-1)
        val description = intent.getStringExtra("description")

        val nutritionId = intent.getIntExtra("id", -1)
        val fakeNutrition = FakeNutritionData.nutritionData.find { it.id == nutritionId }

        if (fakeNutrition != null) {
            //setDetailedData(fakeNutrition)

            binding.detailTitleText.text = name
            binding.detailSubtitleText.text = description
            binding.detailImage.setImageResource(avatarUrl)

            val sectionsPagerAdapter = SectionsPagerAdapter(this)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs,viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }
    }

//    private fun setDetailedData(nutrition: Nutrition) {
//        // Set data to UI elements
//        binding.detailCarbText.text = "Carb: ${nutrition.carb} g"
//        binding.detailProteinText.text = "Protein: ${nutrition.protein} g"
//        binding.detailFatText.text = "Fat: ${nutrition.fat} g"
//        binding.detailVitaminText.text = "Vitamins: ${nutrition.vitamins}"
//        binding.detailMineralText.text = "Minerals: ${nutrition.minerals}"
//    }
}