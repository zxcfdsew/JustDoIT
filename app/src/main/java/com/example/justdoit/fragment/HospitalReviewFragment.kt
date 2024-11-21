package com.example.justdoit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.HospitalReviewAdapter
import com.example.justdoit.databinding.FragmentHospitalReviewBinding
import com.example.justdoit.datas.ReviewData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class HospitalReviewFragment(private val hospitalId: String) : Fragment() {

    private var mBinding: FragmentHospitalReviewBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private var reviews: ArrayList<ReviewData> = arrayListOf()
    private var currentUserNickname:String = ""
    private val mAuth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHospitalReviewBinding.inflate(inflater, container, false)

        mStore.collection("Accounts").document(mAuth.currentUser?.uid.toString()).get().addOnCompleteListener { task ->
            if (task.isSuccessful && task.result.exists()) {
                currentUserNickname = task.result.get("nickname").toString()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()
        val db = mStore.collection("HospitalList").document(hospitalId)

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)

        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result.exists()) {
                    val datas = task.result.get("review") as ArrayList<Map<String, String>>
                    for (data in datas) {
                        reviews.add(0, ReviewData(
                            data.get("reviewId").toString(),
                            data.get("writerNickName").toString(),
                            data.get("ratingScore").toString().toFloat(),
                            data.get("review").toString(),
                            data.get("expertUid").toString(),
                            calculateTime(data.get("reviewId").toString()),
                            timeSorting(data.get("reviewId").toString())
                        ))
                    }
                    reviews.sortWith(compareByDescending{it.sorting})
                    binding.reviewCountTxt.text = reviews.size.toString()
                    binding.recyclerView.adapter = HospitalReviewAdapter(reviews, currentUserNickname, hospitalId, object : HospitalReviewAdapter.isHospitalReviewDeleted{
                        override fun reviewDeleted() {
                            binding.reviewCountTxt.text = reviews.size.toString()
                        }
                    })
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        reviews = arrayListOf()
    }

    fun extractTime(reviewId: String) : String{
        val timeString = reviewId.substring(6).toLong()
        val timeFormat = SimpleDateFormat("MM-dd hh:mm:ss")
        val time = timeFormat.format(timeString)
        return time
    }

    fun calculateTime(reviewId: String) : String{
        val postTime = reviewId.substring(6).toLong()
        var curTime = System.currentTimeMillis()
        var diffTime = (curTime - postTime) / 1000
        var result = "0"
        if (diffTime < 60) {
            result = diffTime.toString() + "초 전"
        } else if (60.let { diffTime /= it; diffTime } < 60) {
            result = diffTime.toString() + "분 전"
        } else if (60.let { diffTime /= it; diffTime } < 24) {
            result = diffTime.toString() + "시간 전"
        } else if (24.let { diffTime /= it; diffTime } < 30) {
            result = diffTime.toString() + "일 전"
        } else if (30.let { diffTime /= it; diffTime } < 12) {
            result = diffTime.toString() + "달 전"
        } else {
            result = diffTime.toString() + "년 전"
        }
        return result
    }

    fun timeSorting(reviewId: String) : Long {
        return reviewId.substring(6).toLong()
    }

}