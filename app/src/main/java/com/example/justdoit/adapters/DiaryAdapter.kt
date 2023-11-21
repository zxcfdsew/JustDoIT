package com.example.justdoit.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.activity.DiarydetailActivity
import com.example.justdoit.datas.Diary

class DiaryAdapter(private val diaries: ArrayList<Diary>) : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    
    inner class DiaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTv)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTv)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTv)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTv)

        init {
            itemView.setOnClickListener {
                val diaryItem = diaries[adapterPosition].date
                Intent(itemView.context, DiarydetailActivity::class.java).apply {
                    putExtra("day", diaryItem)
                    itemView.context.startActivity(this)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_diary, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diary = diaries[position]
        holder.dateTextView.text = diary.date
        holder.titleTextView.text = diary.title
        holder.contentTextView.text = diary.content
        holder.timeTextView.text = diary.time
    }

    override fun getItemCount(): Int {
        return diaries.size
    }
}

