package com.example.justdoit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.R
import com.example.justdoit.adapters.ExpertReviewAdapter
import com.example.justdoit.databinding.FragmentExpertReviewBinding
import com.example.justdoit.datas.ExpertReview

class ExpertReviewFragment : Fragment() {

    private var mBinding: FragmentExpertReviewBinding? = null
    private val binding get() = mBinding!!

    var ratingScore = 4.6F

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val reviews = arrayListOf(
            ExpertReview("사과", 3, "노잼"),
            ExpertReview("바나나", 5, "좋습니다")
        )
        var starSum = 0
        for (i in reviews) {
            for (j in i.star.toString()) {
                starSum += j.toInt()
            }
        }
        ratingScore = (((reviews.size*5).toFloat())/starSum)

        binding.reviewCountTxt.text = reviews.size.toString()

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = ExpertReviewAdapter(reviews)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}