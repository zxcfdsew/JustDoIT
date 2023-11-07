package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.justdoit.databinding.ActivityCommunityAddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommunityAddActivity : AppCompatActivity() {

    private var mBinding: ActivityCommunityAddBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mAuth: FirebaseAuth
    private val mStore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommunityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "글 작성"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mAuth = Firebase.auth
        Log.d("uid", mAuth.toString())

    }

    override fun onResume() {
        super.onResume()
        binding.categoryTv.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }

        binding.addTv.setOnClickListener {
            val title = binding.titleEdt.text.toString()
            val content = binding.contentEdt.text.toString()
            val uid = mAuth.currentUser!!.uid
            Log.d("uid",uid)
            if(title.isEmpty() && content.isEmpty()&& intent.getStringExtra("category").isNullOrEmpty()){
                Toast.makeText(this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
            }else{

                var communitylist = hashMapOf(
                    "title" to binding.titleEdt.text.toString(),
                    "content" to content,
//                    "category" to intent.getStringExtra("category")
                )
                Log.d("community", communitylist.toString())
                //파이어스토어에 저장
                mStore.collection("Community").document(uid).set(communitylist)
                    .addOnSuccessListener {
                        Toast.makeText(this, "작성되었습니다.", Toast.LENGTH_SHORT).show()
                    }

       }


        }
    }

}