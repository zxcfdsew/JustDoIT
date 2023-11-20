package com.example.justdoit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.R
import com.example.justdoit.adapters.HospitalReviewAdapter
import com.example.justdoit.databinding.FragmentHospitalReviewBinding
import com.example.justdoit.datas.ReviewData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HospitalReviewFragment(private val hospitalId: String) : Fragment() {

    private var mBinding: FragmentHospitalReviewBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private var reviews: ArrayList<ReviewData> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHospitalReviewBinding.inflate(inflater, container, false)
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
                        reviews.add(ReviewData(
                            data.get("writerNickName").toString(),
                            data.get("ratingScore").toString().toFloat(),
                            data.get("review").toString())
                        )
                    }
                    binding.reviewCountTxt.text = reviews.size.toString()
                    binding.recyclerView.adapter = HospitalReviewAdapter(reviews)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        reviews = arrayListOf()
    }

}