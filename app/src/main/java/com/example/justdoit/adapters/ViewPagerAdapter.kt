package com.example.justdoit.adapters

import android.os.Bundle
import android.provider.Settings.Global.putInt
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.justdoit.fragment.CommulistFragment


class ViewPagerAdapter(fragment: Fragment, private val pageCount: Int) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = pageCount

    override fun createFragment(position: Int): Fragment {
        val fragment = CommulistFragment()
        fragment.arguments = Bundle().apply {
            putInt("position", position)
            Log.d("눌렀을때",position.toString())
        }
        return fragment
    }
}