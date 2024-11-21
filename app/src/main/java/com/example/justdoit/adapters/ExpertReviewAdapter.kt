package com.example.justdoit.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.databinding.ExpertReviewItemBinding
import com.example.justdoit.datas.ReviewData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertReviewAdapter(
    val reviews: ArrayList<ReviewData>,
    val currentUserNickname: String,
    val documentId: String,
    private val listener: ExpertReviewChange
) :
    RecyclerView.Adapter<ExpertReviewAdapter.ViewHolder>() {

    val mStore = Firebase.firestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExpertReviewAdapter.ViewHolder {
        val view =
            ExpertReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertReviewAdapter.ViewHolder, position: Int) {
        holder.bind(reviews[position])
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class ViewHolder(private val binding: ExpertReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reviewData: ReviewData) {
            binding.nicknameTxt.text = reviewData.nickname
            binding.detailTxt.text = reviewData.detail
            binding.ratingBar.rating = reviewData.star
            binding.timeTxt.text = reviewData.writtedTime

            if (currentUserNickname.equals(reviewData.nickname)) {
                binding.deleteReivewTxt.visibility = View.VISIBLE
            }

            binding.deleteReivewTxt.setOnClickListener {
                val dialogView =
                    LayoutInflater.from(binding.root.context).inflate(R.layout.custom_dialog, null)
                val yesBtn = dialogView.findViewById<Button>(R.id.yes)
                val noBtn = dialogView.findViewById<Button>(R.id.no)
                val builder = AlertDialog.Builder(binding.root.context).setView(dialogView).create()
                builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
                builder.show()
                yesBtn.setOnClickListener {
                    mStore.collection("ExpertList")
                        .document(documentId)
                        .get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val reviewDatas =
                                    task.result.get("review") as ArrayList<Map<String, String>>
                                reviewDatas.removeIf { it["reviewId"] == reviewData.reviewId }

                                mStore.collection("ExpertList")
                                    .document(documentId)
                                    .update("review", reviewDatas)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            reviews.removeAt(adapterPosition)
                                            notifyItemRemoved(adapterPosition)
                                            listener.deleteReview()
                                            builder.dismiss()
                                        }
                                    }

                            }
                        }
                }
                noBtn.setOnClickListener {
                    builder.dismiss()
                }
            }
        }
    }

    interface ExpertReviewChange {
        fun deleteReview()
    }

}