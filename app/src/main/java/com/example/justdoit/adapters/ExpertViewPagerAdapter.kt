package com.example.justdoit.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justdoit.fragment.ExpertInfoFragment
import com.example.justdoit.fragment.ExpertReviewFragment

class ExpertViewPagerAdapter(fragment: FragmentActivity, private val pageCount: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return pageCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> ExpertInfoFragment()
            else -> ExpertReviewFragment()
        }
    }


}