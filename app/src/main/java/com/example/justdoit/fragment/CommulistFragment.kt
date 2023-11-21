package com.example.justdoit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.CommunityAdapter
import com.example.justdoit.databinding.FragmentCommulistBinding
import com.example.justdoit.datas.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
        if (category == 0) {
            Log.d("카테고리리스트1","실행")
            mStore.collection("Community").orderBy("time", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        communityList.clear()

                        for (document in task.result) {

                            mStore.collection("Community").document(document["documentUid"] as String).get()
                                .addOnSuccessListener { documentSnapshot ->
                                    val heartCount = documentSnapshot.get("heartCount") as? ArrayList<String>
                                    val heartSize = heartCount?.size ?: 0
                                    val item = Community(
                                        document["title"] as String,
                                        document["content"] as String,
                                        document["time"] as String,
                                        document["category"] as String,
                                        document["documentUid"] as String,
                                        document["uid"] as String,
                                        heartSize.toString()
                                    )
                                    communityList.add(item)
                                    Log.d("카테고리리스트111",communityList.toString())
                                    binding.recyclerView.layoutManager =
                                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                                    binding.recyclerView.adapter = CommunityAdapter(communityList)
                                }
                            Log.d("카테고리리스트1",communityList.toString())

                        }
                        Log.d("카테고리리스트1",communityList.toString())


                    }
                }
        } else {
            Log.d("카테고리리스트2","실행")
            mStore.collection("Community").whereEqualTo("category", category).orderBy("time",Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("2성공",task.result.toString())
                        for (document in task.result) {

                            mStore.collection("Community").document(document["documentUid"] as String).get()
                                .addOnSuccessListener { documentSnapshot ->
                                    val heartCount = documentSnapshot.get("heartCount") as? ArrayList<String>
                                    val heartSize = heartCount?.size ?: 0
                                    val item = Community(
                                        document["title"] as String,
                                        document["content"] as String,
                                        document["time"] as String,
                                        document["category"] as String,
                                        document["documentUid"] as String,
                                        document["uid"] as String,
                                        heartSize.toString()
                                    )
                                    communityList.add(item)
                                    Log.d("카테고리리스트111",communityList.toString())
                                    binding.recyclerView.layoutManager =
                                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                                    binding.recyclerView.adapter = CommunityAdapter(communityList)
                                }
                            Log.d("카테고리리스트1",communityList.toString())

                        }

                    binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    binding.recyclerView.adapter = CommunityAdapter(communityList)
                    }
                }
                .addOnFailureListener {exception ->
                    Log.d("카테고리리스트2", "실행안됨", exception)
                }

        }
    }
}


