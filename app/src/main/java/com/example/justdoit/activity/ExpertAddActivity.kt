package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityExpertAddBinding
import com.example.justdoit.datas.ExpertInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpertAddBinding
    private val mStore: FirebaseFirestore = Firebase.firestore
    private val mAuth: FirebaseAuth = Firebase.auth
    private var expertUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "전문가 등록하기"

        expertUid = mStore.collection("ExpertList").document().id

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.saveItem -> {
                var expertInfo = ExpertInfo(
                    expertUid!!,
                    binding.nameEdt.text.toString(),
                    binding.availableTimeEdt.text.toString(),
                    binding.phoneNumEdt.text.toString(),
                    binding.introduceEdt.text.toString()
                )
                binding.nameEdt.text.toString()
                mStore.collection("ExpertList").document(expertUid!!).set(expertInfo).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "정상적으로 추가되었습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}