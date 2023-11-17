package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import com.example.justdoit.R
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.databinding.ActivityExpertProfileBinding
import com.example.justdoit.fragment.ExpertInfoFragment
import com.example.justdoit.fragment.ExpertReviewFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertProfileActivity : AppCompatActivity() {

    private var mBinding : ActivityExpertProfileBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore

    var testData = "nothing"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExpertProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val expertUid = intent.getStringExtra("expertUid").toString()

        testData = expertUid

        val db = mStore.collection("ExpertList").document(expertUid!!)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val datas = task.result
                binding.nameTxt.text = datas.get("name").toString()
                binding.ratingScore1Txt.text = datas.get("score").toString()
                binding.ratingScore2Txt.text = datas.get("score").toString()
                binding.ratingBar.rating = datas.get("score").toString().toFloat()
            }
        }

        binding.viewPager.adapter = ExpertViewPagerAdapter(this, 2, testData)
        binding.viewPager.isUserInputEnabled = false    // 스와이프 막기
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "정보"
                1 -> tab.text = "리뷰"
            }
        }.attach()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun datsSendingTest() {
        val bundle: Bundle = Bundle()

        val message = "testing"
        bundle.putString("data", message)


    }



}