package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.justdoit.databinding.ActivityMyheartBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyheartActivity : AppCompatActivity() {

    private var mBinding : ActivityMyheartBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private val mAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMyheartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val currentUserUid = mAuth.currentUser?.uid.toString()

        mStore.collection("ExpertList").whereArrayContains("favorite", currentUserUid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (data in task.result) {
                    Log.d("testHeart", data.get("expertUid").toString())
                }
            }
        }

    }
}