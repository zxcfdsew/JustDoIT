package com.example.justdoit.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import com.example.justdoit.R
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.databinding.ActivityExpertProfileBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertProfileActivity : AppCompatActivity() {

    private var mBinding : ActivityExpertProfileBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private val mAuth: FirebaseAuth = Firebase.auth
    private lateinit var userNickname: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExpertProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val userUid = mAuth.currentUser?.uid
        mStore.collection("Accounts").document(userUid.toString()).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userNickname = task.result.get("nickname").toString()
            }
        }

        val expertUid = intent.getStringExtra("expertUid").toString()

        val db = mStore.collection("ExpertList").document(expertUid!!)

        binding.favoriteIv.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                Log.d("imgTest", "true")
            } else {
                Log.d("imgTest", "false")
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "전문가 정보"

        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val datas = task.result
                binding.expertNameTxt.text = datas.get("name").toString()
                binding.availableTimeTxt.text = datas.get("availableTime").toString()
                binding.phoneNumTxt.text = datas.get("phoneNum").toString()

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

        binding.viewPager.adapter = ExpertViewPagerAdapter(this, 2, expertUid)
        binding.viewPager.isUserInputEnabled = false    // 스와이프 막기
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "소개"
                1 -> tab.text = "리뷰"
            }
        }.attach()

        binding.reviewBtn.setOnClickListener {
            val reviewIntent = Intent(this, AddReviewActivity::class.java)
            reviewIntent.putExtra("from", "expert")
            reviewIntent.putExtra("uid", expertUid)
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