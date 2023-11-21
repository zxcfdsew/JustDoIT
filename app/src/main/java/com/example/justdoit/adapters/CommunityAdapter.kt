package com.example.justdoit.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.activity.CommudetaillActivity
import com.example.justdoit.datas.Comment
import com.example.justdoit.datas.Community

class CommunityAdapter(var item: ArrayList<Community>) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_community,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTv.text = item[position].title
        holder.contentTv.text = item[position].content
        holder.timeTv.text = item[position].time
        holder.heartCountTv.text = item[position].heartCount
        holder.sadCountTv.text = item[position].sadCount
        holder.commentCountTv.text = item[position].commentCount
        holder.documentId.text = item[position].documentUid
        holder.uid.text = item[position].uid

    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var titleTv:TextView = itemView.findViewById(R.id.titleTv)
        var contentTv:TextView = itemView.findViewById(R.id.contentTv)
        var timeTv:TextView = itemView.findViewById(R.id.timeTv)
        var heartCountTv:TextView = itemView.findViewById(R.id.heartCountTv)
        var sadCountTv:TextView = itemView.findViewById(R.id.sadCountTv)
        var commentCountTv:TextView = itemView.findViewById(R.id.commentCountTv)
        var documentId:TextView = itemView.findViewById(R.id.documentId)
        var uid:TextView = itemView.findViewById(R.id.uid)


        init {
            itemView.setOnClickListener {
                val documentUid= item[adapterPosition].documentUid.toString()
                val category= item[adapterPosition].category.toString()
                val uid= item[adapterPosition].uid.toString()
                Intent(itemView.context, CommudetaillActivity::class.java).apply {
                putExtra("documentUid", documentUid)
                    putExtra("uid", uid)
                    putExtra("category", category)
                    Log.d("아이템", documentUid)
                    Log.d("아이템", uid)
                itemView.context.startActivity(this)
                }
            }

            }

        }
    }

