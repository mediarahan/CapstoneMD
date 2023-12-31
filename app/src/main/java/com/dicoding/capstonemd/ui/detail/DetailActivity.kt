package com.dicoding.capstonemd.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.capstonemd.R
import com.dicoding.capstonemd.adapter.SectionsPagerAdapter
import com.dicoding.capstonemd.data.local.fake.FakeNutritionData
import com.dicoding.capstonemd.databinding.ActivityDetailBinding
import com.dicoding.capstonemd.ui.settings.SettingsActivity
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

        setSupportActionBar(binding.toolbar)

        val name = intent.getStringExtra("name")
        val avatarUrl = intent.getIntExtra("avatar", -1)
        val description = intent.getStringExtra("description")

        val nutritionId = intent.getIntExtra("id", -1)
        val fakeNutrition = FakeNutritionData.nutritionData.find { it.id == nutritionId }

        if (fakeNutrition != null) {
            //setDetailedData(fakeNutrition)
            binding.detailTitleText.text = name
            binding.detailSubtitleText.text = description

            if (avatarUrl != -1) {
                binding.detailImage.setImageResource(avatarUrl)
            } else {
                binding.detailImage.setImageResource(R.drawable.logo)
            }

            val sectionsPagerAdapter = SectionsPagerAdapter(this, name)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            supportActionBar?.elevation = 0f
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val customActionBar = layoutInflater.inflate(R.layout.custom_action_bar, null)
        val customImageView: ImageView = customActionBar.findViewById(R.id.customImageView)
        customImageView.setImageResource(R.drawable.logo)

        supportActionBar?.customView = customActionBar
        supportActionBar?.elevation = 0f
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            R.id.menu_hidden_gem -> {
                val intent = Intent(this@DetailActivity, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

}