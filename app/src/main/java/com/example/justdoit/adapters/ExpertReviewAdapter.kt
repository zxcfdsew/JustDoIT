package com.example.justdoit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.databinding.ExpertReviewItemBinding
import com.example.justdoit.datas.ReviewData

class ExpertReviewAdapter(val reviews: ArrayList<ReviewData>) : RecyclerView.Adapter<ExpertReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpertReviewAdapter.ViewHolder {
        val view = ExpertReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertReviewAdapter.ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ViewHolder(private val binding: ExpertReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData: ReviewData) {
            binding.nicknameTxt.text = reviewData.nickname
            binding.detailTxt.text = reviewData.detail
            binding.ratingBar.rating = reviewData.star
        }
    }
}