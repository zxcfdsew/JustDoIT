package com.example.justdoit.adapters

import android.content.Intent.getIntent
import android.system.Os.remove
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.datas.Comment
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CommentAdapter(var item: ArrayList<Comment>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private val mStore = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        holder.nickNameTv.text = item[position].nickname
        holder.commentTv.text = item[position].comment
        holder.timeTv.text = item[position].posttime
        holder.commentId.text = item[position].commentId
        holder.writeUid.text = item[position].writeUid
    }


    override fun getItemCount(): Int {
        return item.size
    }

    fun removeData(position: Int, documentId : String) {
        Log.d("받은 다큐먼트 아이디", documentId)
        Log.d("포지션",position.toString())


        mStore.collection("Community").document(documentId).get()
            .addOnSuccessListener { documentSnapshot ->
                val comments = documentSnapshot.get("comments") as? ArrayList<HashMap<String, String>>

                if (comments != null && comments.isNotEmpty()) { // 배열이 비어있지 않은 경우에만 처리
                    val commentIdToRemove = item[position].commentId.toString()
                    Log.d("포지션",position.toString())
//                  for(data in comments){
//                      var item = data["commentId"]
//                     if( data["commentId"] == commentIdToRemove){
//                         remove()
//                     }
//                  }
                    comments.removeIf { comment ->
                        comment["commentId"] == commentIdToRemove
                    }

                    Log.d("댓글 삭제 내역", comments.toString())
                    val updates = hashMapOf<String, Any>(
                        "comments" to comments
                    )
                    mStore.collection("Community").document(documentId).update(updates)
                        .addOnSuccessListener {

                            notifyItemRemoved(position)

                        }
                    item.removeAt(position)
                }

            }



    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nickNameTv: TextView = itemView.findViewById(R.id.nickNameTv)
        var commentTv: TextView = itemView.findViewById(R.id.commentTv)
        var timeTv: TextView = itemView.findViewById(R.id.timeTv)
        var commentId: TextView = itemView.findViewById(R.id.commentId)
        var writeUid: TextView = itemView.findViewById(R.id.writeUid)

    }


}