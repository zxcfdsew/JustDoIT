package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityAddReviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding
    private val mStore = Firebase.firestore
    private lateinit var mCollection: String
    private lateinit var uid: String
    private lateinit var userNickName: String
    private val mAuth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getintent = intent
        val from = getintent.getStringExtra("from")
        uid = getintent.getStringExtra("uid")!!
        userNickName = getintent.getStringExtra("userNickName")!!
        setTheme(R.style.Theme_JustDoIT_Hospital)

        if (from.equals("expert")) {
            mCollection = "ExpertList"
            setTheme(R.style.Theme_JustDoIT)
        } else if (from.equals("hospital")) {
            mCollection = "HospitalList"
            setTheme(R.style.Theme_JustDoIT_Hospital)
        }

        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (from.equals("expert")) {
            binding.topLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
        } else if (from.equals("hospital")) {
            binding.topLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sky))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.saveItem -> {
                var ratingScore: String = binding.ratingBar.rating.toString()
                var review: String = binding.reviewEdt.text.toString()
                var reviewId: String = "review"+System.currentTimeMillis()
                var data = mapOf("ratingScore" to ratingScore, "review" to review, "reviewId" to reviewId, "writerNickName" to userNickName)
                mStore.collection(mCollection).document(uid).update("review", FieldValue.arrayUnion(data)).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}