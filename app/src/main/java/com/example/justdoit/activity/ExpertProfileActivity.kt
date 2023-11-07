package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.databinding.ActivityExpertProfileBinding
import com.example.justdoit.fragment.ExpertReviewFragment
import com.google.android.material.tabs.TabLayoutMediator

class ExpertProfileActivity : AppCompatActivity() {

    private var mBinding : ActivityExpertProfileBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExpertProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val documentId = intent.getStringExtra("documentId")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewPager.adapter = ExpertViewPagerAdapter(this, 2)
        binding.viewPager.isUserInputEnabled = false    // 스와이프 막기
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "정보"
                1 -> tab.text = "리뷰"
            }
        }.attach()

        var score = ExpertReviewFragment().ratingScore

        binding.nameTxt.text = documentId
        binding.ratingScore1Txt.text = score.toString()
        binding.ratingScore2Txt.text = score.toString()
        binding.ratingBar.rating = score

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}