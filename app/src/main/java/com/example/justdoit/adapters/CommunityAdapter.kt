package com.example.justdoit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.datas.Community

class CommunityAdapter (var item: ArrayList<Community>) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.community_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.contentTv.text = item[position].content
        holder.timeTv.text = item[position].time
        holder.heartCountTv.text = item[position].heartCount
        holder.sadCountTv.text = item[position].sadCount
        holder.commentCountTv.text = item[position].commentCount

    }

    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        var contentTv:TextView = itemView.findViewById(R.id.contentTv)
        var timeTv:TextView = itemView.findViewById(R.id.timeTv)
        var heartCountTv:TextView = itemView.findViewById(R.id.heartCountTv)
        var sadCountTv:TextView = itemView.findViewById(R.id.sadCountTv)
        var commentCountTv:TextView = itemView.findViewById(R.id.commentCountTv)
    }

}