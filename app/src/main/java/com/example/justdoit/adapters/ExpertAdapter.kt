package com.example.justdoit.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.activity.ExpertProfileActivity
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.datas.ExpertInfo

class ExpertAdapter(val ExpertList: ArrayList<ExpertInfo>): RecyclerView.Adapter<ExpertAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertAdapter.ViewHolder {
        val view = ExpertListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view).also { viewHolder ->
            viewHolder.itemView.setOnClickListener {
                val intent = Intent(viewHolder.itemView.context, ExpertProfileActivity::class.java)
                intent.putExtra("expertUid", ExpertList.get(viewHolder.adapterPosition).expertUid)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: ExpertAdapter.ViewHolder, position: Int) {
        holder.bind(ExpertList[position])
    }

    override fun getItemCount(): Int {
        return ExpertList.size
    }

    class ViewHolder(private val binding: ExpertListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(expertInfo: ExpertInfo) {
            binding.profileImg.clipToOutline = true
            binding.nameTxt.text = expertInfo.name
            binding.availableTimeTxt.text = expertInfo.availableTime
            binding.phoneNumTxt.text = expertInfo.phoneNum
        }

    }



}