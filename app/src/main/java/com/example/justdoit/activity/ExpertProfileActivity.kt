package com.example.justdoit.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.justdoit.R
import com.example.justdoit.adapters.ExpertReviewAdapter
import com.example.justdoit.adapters.ExpertViewPagerAdapter
import com.example.justdoit.databinding.ActivityExpertProfileBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ExpertProfileActivity : AppCompatActivity() {

    private var mBinding : ActivityExpertProfileBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private val mAuth: FirebaseAuth = Firebase.auth
    private lateinit var userNickname: String
    private lateinit var expertUid: String
    private val mStorage = FirebaseStorage.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityExpertProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        expertUid = intent.getStringExtra("expertUid").toString()

        val userUid = mAuth.currentUser?.uid

        mStore.collection("Accounts").document(userUid.toString()).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                userNickname = task.result.get("nickname").toString()
            }
        }

        var imageName = "Expert_" + expertUid
        val storageRef = mStorage.reference.child("profileImg").child(imageName)
        storageRef.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(this).load(uri).into(binding.profileImg)
        }

        val db = mStore.collection("ExpertList").document(expertUid!!)

        binding.favoriteIv.setOnClickListener {
            it.isSelected = !it.isSelected
            if (it.isSelected) {
                db.update("favorite", FieldValue.arrayUnion(userUid))
            } else {
                db.update("favorite", FieldValue.arrayRemove(userUid))
            }
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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

    override fun onResume() {
        super.onResume()
        val userUid = mAuth.currentUser?.uid
        val db = mStore.collection("ExpertList").document(expertUid!!)
        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val datas = task.result

                val favorite = datas.get("favorite") as? ArrayList<String>
                if (favorite != null && favorite.contains(userUid)) {
                    binding.favoriteIv.isSelected = true
                }

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

    }

}