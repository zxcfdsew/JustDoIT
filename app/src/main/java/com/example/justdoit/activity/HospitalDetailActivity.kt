package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.adapters.HospitalViewPagerAdapter
import com.example.justdoit.databinding.ActivityHospitalDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HospitalDetailActivity : AppCompatActivity() {

    private var mBinding: ActivityHospitalDetailBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private val mAuth = Firebase.auth
    private lateinit var userNickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHospitalDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getIntent = intent
        val hospitalUid = getIntent.getStringExtra("hospitalUid")
        val db = mStore.collection("HospitalList").document(hospitalUid!!)

        mStore.collection("Accounts").document(mAuth.currentUser?.uid.toString()).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userNickname = task.result.get("nickname").toString()
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "병원 정보"

        binding.favoriteIv.setOnClickListener {
            it.isSelected = !it.isSelected
        }
        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val datas = task.result
                binding.hospitalNameTxt.text = datas.get("name").toString()
                binding.callNumTxt.text = datas.get("hospitalNum").toString()
                binding.addressTxt.text = datas.get("address").toString()
                binding.availableTimeTxt.text = datas.get("availableTime").toString()

                val reviewsData = datas.get("review") as ArrayList<Map<String, String>>
                var reviewCount = 0
                var allScore = 0F
                for (rate in reviewsData) {
                    allScore += rate.get("ratingScore").toString().toFloat()
                    reviewCount++
                }
                var ratingScore = allScore / reviewCount
                var ratingScoreString = if (ratingScore.isNaN()) "0" else String.format("%.1f", ratingScore)
                db.update("score", ratingScoreString)
                binding.ratingTxt.text = ratingScoreString
            }
        }

        binding.viewPager.adapter = HospitalViewPagerAdapter(this, 2, hospitalUid)
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
            reviewIntent.putExtra("uid", hospitalUid)
            reviewIntent.putExtra("userNickName", userNickname)
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