package com.example.justdoit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.databinding.ExpertReviewItemBinding
import com.example.justdoit.datas.ReviewData

class HospitalReviewAdapter(val reviewDatas: ArrayList<ReviewData>) : RecyclerView.Adapter<HospitalReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalReviewAdapter.ViewHolder {
        val view = ExpertReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalReviewAdapter.ViewHolder, position: Int) {
        holder.bind(reviewDatas[position])
    }

    override fun getItemCount(): Int {
        return reviewDatas.size
    }

    class ViewHolder(private val binding: ExpertReviewItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(reviewInfo: ReviewData) {
            binding.nicknameTxt.text = reviewInfo.nickname
            binding.detailTxt.text = reviewInfo.detail
            binding.ratingBar.rating = reviewInfo.star
        }

    }
}