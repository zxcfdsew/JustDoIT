package com.example.justdoit.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityExpertAddBinding
import com.example.justdoit.datas.ExpertInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class ExpertAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpertAddBinding
    private val mStore: FirebaseFirestore = Firebase.firestore
    private val mAuth: FirebaseAuth = Firebase.auth
    private var expertUid: String? = null
    private lateinit var mStorage: FirebaseStorage
    private var profileImage: Uri? = null

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                binding.addProfileImg.visibility = View.INVISIBLE
                profileImage = result.data?.data
                binding.profileImg.setImageURI(profileImage)
            } else {
                Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        expertUid = mStore.collection("ExpertList").document().id

        mStorage = FirebaseStorage.getInstance()

        binding.profileImg.setOnClickListener {
            var imageIntent = Intent(Intent.ACTION_PICK)
            imageIntent.type = "image/*"
            resultLauncher.launch(imageIntent)
        }
        binding.addTv.setOnClickListener {
            var expertInfo = ExpertInfo(
                expertUid!!,
                binding.nameEdt.text.toString(),
                binding.availableTimeEdt.text.toString(),
                binding.phoneNumEdt.text.toString(),
                binding.introduceEdt.text.toString()
            )
            mStore.collection("ExpertList").document(expertUid!!).set(expertInfo)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val defaultData =
                            mapOf("review" to ArrayList<Map<String, String>>(), "score" to "0")
                        mStore.collection("ExpertList").document(expertUid!!)
                            .update(defaultData)
                        uploadToStorage()
                        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        }

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun uploadToStorage() {
        var imageName = "Expert_" + expertUid
        Log.d("imageTest", imageName)
        val storageRef = mStorage.reference.child("profileImg").child(imageName)
        if (profileImage != null) {
            storageRef.putFile(profileImage!!).addOnSuccessListener {
                Log.d("imageUpload", "Success")
            }
        }
    }
}