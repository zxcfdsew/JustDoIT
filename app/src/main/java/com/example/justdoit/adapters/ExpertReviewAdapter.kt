package com.example.justdoit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.databinding.ExpertReviewItemBinding
import com.example.justdoit.datas.ExpertReview

class ExpertReviewAdapter(val reviews: ArrayList<ExpertReview>) : RecyclerView.Adapter<ExpertReviewAdapter.ViewHolder>() {

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
        fun bind(expertReview: ExpertReview) {
            binding.nicknameTxt.text = expertReview.nickname
            binding.detailTxt.text = expertReview.detail
            binding.ratingBar.rating = expertReview.star
        }
    }
}