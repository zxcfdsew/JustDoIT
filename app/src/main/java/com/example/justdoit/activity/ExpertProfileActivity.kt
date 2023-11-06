package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.databinding.ActivityExpertProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ExpertProfileActivity : AppCompatActivity() {

    private var mBinding : ActivityExpertProfileBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExpertProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        binding.viewPager.adapter = ExpertViewPagerAdapter(supportFragmentManager, 2)
//        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
//            when(position) {
//                0 -> tab.text = "정보"
//                1 -> tab.text = "리뷰"
//            }
//        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}