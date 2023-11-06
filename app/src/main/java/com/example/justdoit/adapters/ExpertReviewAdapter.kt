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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expert_review_item, parent, false)
        return ViewHolder(ExpertReviewItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ExpertReviewAdapter.ViewHolder, position: Int) {
        holder.binding.nicknameTxt.text = reviews.get(position).nickname
        holder.binding.detailTxt.text = reviews.get(position).detail
        holder.binding.ratingBar.rating = reviews.get(position).star.toFloat()

    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ViewHolder(val binding: ExpertReviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}