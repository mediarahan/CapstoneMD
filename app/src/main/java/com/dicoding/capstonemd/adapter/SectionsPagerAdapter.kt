package com.dicoding.capstonemd.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.capstonemd.ui.detail.TabMapsFragment
import com.dicoding.capstonemd.ui.detail.TabNutritionFragment

class SectionsPagerAdapter (activity: AppCompatActivity, private val name: String?): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = TabMapsFragment.newInstance(name)
            1 -> fragment = TabNutritionFragment()
            else -> throw IllegalArgumentException("Invalid tab position")
        }
        return fragment as Fragment
    }
}