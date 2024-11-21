package com.example.justdoit.adapters

import android.app.AlertDialog
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

class HospitalReviewAdapter(
    val reviewDatas: ArrayList<ReviewData>,
    val currentUserNickname: String,
    val documentId: String,
    private val listener: isHospitalReviewDeleted
) : RecyclerView.Adapter<HospitalReviewAdapter.ViewHolder>() {

    private val mStore = Firebase.firestore

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalReviewAdapter.ViewHolder {
        val view =
            ExpertReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalReviewAdapter.ViewHolder, position: Int) {
        holder.bind(reviewDatas[position])
    }

    override fun getItemCount(): Int {
        return reviewDatas.size
    }

    inner class ViewHolder(private val binding: ExpertReviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reviewInfo: ReviewData) {
            binding.nicknameTxt.text = reviewInfo.nickname
            binding.detailTxt.text = reviewInfo.detail
            binding.ratingBar.rating = reviewInfo.star
            binding.timeTxt.text = reviewInfo.writtedTime

            if (reviewInfo.nickname.equals(currentUserNickname)) {
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
                    mStore.collection("HospitalList")
                        .document(documentId)
                        .get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val removedata =
                                    task.result.get("review") as ArrayList<Map<String, String>>
                                removedata.removeIf { it["reviewId"] == reviewInfo.reviewId }

                                mStore.collection("HospitalList")
                                    .document(documentId)
                                    .update("review", removedata)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            reviewDatas.removeAt(adapterPosition)
                                            notifyItemRemoved(adapterPosition)
                                            listener.reviewDeleted()
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

    interface isHospitalReviewDeleted {
        fun reviewDeleted()
    }
}