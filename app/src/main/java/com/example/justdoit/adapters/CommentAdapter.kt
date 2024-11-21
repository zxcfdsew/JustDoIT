package com.example.justdoit.adapters

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.databinding.ItemCommentBinding
import com.example.justdoit.datas.Comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentAdapter(var item: ArrayList<Comment>, var documentId:String, private val listener:Delete) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private val mStore = Firebase.firestore
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val view = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    fun removeData(position: Int, documentId : String) {
        Log.d("받은 다큐먼트 아이디", documentId)
        Log.d("포지션",position.toString())


    }
    fun calculateTime(reviewId: String) : String{
        val postTime = reviewId.toLong()
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


    inner class ViewHolder(private val binding:ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        val uid = mAuth.currentUser!!.uid
        fun bind(items:Comment){
            binding.nickNameTv.text =  items.nickname
            binding.commentTv.text = items.comment
            binding.timeTv.text = calculateTime(items.posttime.toString())
            binding.commentId.text = items.commentId
            binding.writeUid.text = items.writeUid
            if (uid == items.writeUid){
                binding.deleteBtn.isVisible = true
            }else{
                binding.deleteBtn.isGone = true
            }
            binding.deleteBtn.setOnClickListener {

                val dialogView = LayoutInflater.from(binding.root.context).inflate(R.layout.custom_dialog, null)

                val yesBtn = dialogView.findViewById<Button>(R.id.yes)
                val noBtn = dialogView.findViewById<Button>(R.id.no)

                val builder = AlertDialog.Builder(binding.root.context).setView(dialogView).create()
                builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
                builder.show()
                yesBtn.setOnClickListener {
                    mStore.collection("Community").document(documentId).get()
                        .addOnSuccessListener { documentSnapshot ->
                            val comments = documentSnapshot.get("comments") as? ArrayList<HashMap<String, String>>

                            if (comments != null && comments.isNotEmpty()) { // 배열이 비어있지 않은 경우에만 처리
                                val commentIdToRemove = item[position].commentId.toString()
                                Log.d("포지션",position.toString())
                                comments.removeIf { comment ->
                                    comment["commentId"] == commentIdToRemove
                                }

                                Log.d("댓글 삭제 내역", comments.toString())
                                val updates = hashMapOf<String, Any>(
                                    "comments" to comments
                                )
                                mStore.collection("Community").document(documentId).update(updates)
                                    .addOnSuccessListener {
                                        item.removeAt(position)
                                        notifyDataSetChanged()
                                        listener.delete()
                                        builder.dismiss()

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
    interface Delete{
        fun delete()
    }



}