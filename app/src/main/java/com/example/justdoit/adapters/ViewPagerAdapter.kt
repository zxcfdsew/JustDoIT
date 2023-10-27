package com.example.justdoit.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justdoit.fragment.SubCommuFragment


class ViewPagerAdapter(fragment: Fragment, private val pageCount: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = pageCount

    override fun createFragment(position: Int): Fragment {
        return SubCommuFragment()
    }
}