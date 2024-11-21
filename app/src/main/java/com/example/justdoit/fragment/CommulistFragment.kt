package com.example.justdoit.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.CommunityAdapter
import com.example.justdoit.databinding.FragmentCommulistBinding
import com.example.justdoit.datas.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime


class CommulistFragment : Fragment() {

    private var mBinding: FragmentCommulistBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mAuth: FirebaseAuth
    private val mStore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCommulistBinding.inflate(inflater, container, false)

        mAuth = Firebase.auth
        val uid = mAuth.currentUser!!.uid



        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val position = arguments?.getInt("position", 0)
        val category = when (position) {
            0 -> 0
            1 -> "일반 고민"
            2 -> "취업/진로"
            3 -> "직장"
            4 -> "연애"
            5 -> "성추행"
            6 -> "결혼/육아"
            7 -> "대인관계"
            8 -> "외모"
            9 -> "가족"
            else -> 1
        }
        Log.d("카테고리", category.toString())


        var communityList = ArrayList<Community>()
        val current = LocalDateTime.now().toString()
        if (category == 0) {
            mStore.collection("Community").orderBy("time", Query.Direction.DESCENDING).get()
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
                                    if (current.substring(0, 10) == addTime.substring(0, 10)) {
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
                                    binding.recyclerView.layoutManager = LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                    val reverseComparator =
                                        compareByDescending<Community> { it.time }
                                    communityList.sortWith(reverseComparator)
                                    Log.d("정렬 후 리스트", communityList.toString())
                                    binding.recyclerView.adapter = CommunityAdapter(communityList)
                                }
                        }
                    }
                }
        } else {
            Log.d("카테고리리스트2", "실행")
            mStore.collection("Community").whereEqualTo("category", category)
                .orderBy("time", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        communityList.clear()
                        Log.d("2성공", task.result.toString())
                        for (document in task.result) {

                            mStore.collection("Community")
                                .document(document["documentUid"] as String).get()
                                .addOnSuccessListener { documentSnapshot ->
                                    val heartCount =
                                        documentSnapshot.get("heartCount") as? ArrayList<String>
                                    val heartSize = heartCount?.size ?: 0
                                    val addTime = document["time"] as String
                                    var formatTime = ""
                                    if (current.substring(0, 10) == addTime.substring(0, 10)) {
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
                                        heartSize.toString()
                                    )
                                    communityList.add(item)
                                    Log.d("정렬 전 리스트 반보", communityList.toString())
                                    binding.recyclerView.layoutManager = LinearLayoutManager(
                                        context,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                    val reverseComparator =
                                        compareByDescending<Community> { it.time }
                                    communityList.sortWith(reverseComparator)
                                    Log.d("정렬 후 리스트2", communityList.toString())
                                    binding.recyclerView.adapter = CommunityAdapter(communityList)
                                    Log.d("카테고리리스트111", communityList.toString())
                                }
                            Log.d("카테고리리스트1", communityList.toString())

                        }
                    }
                }
        }
    }
}


