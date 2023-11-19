package com.example.justdoit.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justdoit.fragment.HospitalInfoFragment
import com.example.justdoit.fragment.HospitalReviewFragment

class HospitalViewPagerAdapter(fragment: FragmentActivity, private val pageCount: Int, private val hospitalId: String): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return pageCount
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> HospitalInfoFragment(hospitalId)
            else -> HospitalReviewFragment(hospitalId)
        }
    }
}