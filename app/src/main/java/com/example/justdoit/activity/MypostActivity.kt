package com.example.justdoit.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.CommunityAdapter
import com.example.justdoit.databinding.ActivityMypostBinding
import com.example.justdoit.datas.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class MypostActivity : AppCompatActivity() {
    private var mBinding : ActivityMypostBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private lateinit var mAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMypostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        mAuth = Firebase.auth

        val currentUid = mAuth.currentUser!!.uid
        val currentTime = LocalDateTime.now().toString()
        val communityList = ArrayList<Community>()

        mStore.collection("Community").whereEqualTo("uid",currentUid).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    communityList.clear()
                    for (document in task.result) {
                        mStore.collection("Community")
                            .document(document["documentUid"] as String).get()
                            .addOnSuccessListener { documentSnapshot ->
                                val heartCount =
                                    documentSnapshot.get("heartCount") as? ArrayList<String>
                                val heartSize = heartCount?.size ?: 0
                                val commentCount =
                                    documentSnapshot.get("comments") as? ArrayList<HashMap<String, String>>
                                val commentSize = commentCount?.size ?: 0
                                val addTime = document["time"] as String
                                var formatTime = ""
                                if (currentTime.substring(0, 10) == addTime.substring(0, 10)) {
                                    formatTime = addTime.substring(11, 16)
                                } else {
                                    formatTime = addTime.substring(0, 10)
                                }

                                val item = Community(
                                    document["title"] as String,
                                    document["content"] as String,
                                    formatTime,
                                    document["category"] as String,
                                    document["documentUid"] as String,
                                    document["uid"] as String,
                                    heartSize.toString(),
                                    commentSize.toString()
                                )
                                communityList.add(item)
                                Log.d("정렬 전 리스트 반보", communityList.toString())
                                binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                                val reverseComparator = compareByDescending<Community> { it.time }
                                communityList.sortWith(reverseComparator)
                                Log.d("정렬 후 리스트", communityList.toString())
                                binding.recyclerView.adapter = CommunityAdapter(communityList)
                            }
                    }
                }
            }
    }
}