package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.adapters.HospitalViewPagerAdapter
import com.example.justdoit.databinding.ActivityHospitalDetailBinding
import com.google.android.material.tabs.TabLayoutMediator

class HospitalDetailActivity : AppCompatActivity() {

    private var mBinding: ActivityHospitalDetailBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "병원 정보"

        binding.favoriteIv.setOnClickListener {
            it.isSelected = !it.isSelected
        }

        binding.viewPager.adapter = HospitalViewPagerAdapter(this, 2, "testUid")
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            when (position) {
                0 -> tab.text = "소개"
                1 -> tab.text = "리뷰"
            }
        }.attach()

        binding.reviewBtn.setOnClickListener {
            val reviewIntent = Intent(this, AddReviewActivity::class.java)
            reviewIntent.putExtra("from", "hospital")
            startActivity(reviewIntent)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}