package com.capstone.ditalent.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.ditalent.ui.fragments.slide.SlideFragment

class BoardingPageAdapter(
    activity: AppCompatActivity,
    private val slides: List<SlideFragment> = emptyList()
): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return slides.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        return slides.forEachIndexed { index, slideFragment ->
            if (position == index) {
                fragment = slideFragment
            }
        }.run {
            fragment as Fragment
        }
    }

}