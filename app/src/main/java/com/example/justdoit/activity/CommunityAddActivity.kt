package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.justdoit.databinding.ActivityCommunityAddBinding

import com.google.firebase.firestore.FirebaseFirestore

class CommunityAddActivity : AppCompatActivity() {

    private var mBinding: ActivityCommunityAddBinding? = null
    private val binding get() = mBinding!!
    private var fbFirestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommunityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "글 작성"

        //작성 눌럿을때 : 제목, 내용, 카테고리 저장, 카테고리 입력확인
//        binding.addTv.setOnClickListener {
//            fbFirestore?.collection("Community")?.document("")
//        }
//
        binding.categoryTv.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }


    }
}