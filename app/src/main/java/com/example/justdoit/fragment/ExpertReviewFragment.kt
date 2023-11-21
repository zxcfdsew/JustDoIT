package com.example.justdoit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.ExpertReviewAdapter
import com.example.justdoit.databinding.FragmentExpertReviewBinding
import com.example.justdoit.datas.ReviewData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertReviewFragment(val Uid: String) : Fragment() {

    private var mBinding: FragmentExpertReviewBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore
    private var reviews: ArrayList<ReviewData> = arrayListOf()
    private var ratingScore = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentExpertReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)

        val db = mStore.collection("ExpertList").document(Uid)
        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result.exists()) {
                    val datas = task.result.get("review") as ArrayList<Map<String, String>>
                    for (data in datas) {
                        reviews.add(ReviewData(
                            data.get("writerNickName").toString(),
                            data.get("ratingScore").toString().toFloat(),
                            data.get("review").toString()))
                    }
                    binding.reviewCountTxt.text = reviews.size.toString()
                    binding.recyclerView.adapter = ExpertReviewAdapter(reviews)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        reviews = arrayListOf()
    }

}