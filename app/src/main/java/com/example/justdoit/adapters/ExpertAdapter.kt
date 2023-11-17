package com.example.justdoit.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.activity.ExpertProfileActivity
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.datas.ExpertInfo
import com.example.justdoit.datas.PublicDatas

class ExpertAdapter(val ExpertList: ArrayList<ExpertInfo>): RecyclerView.Adapter<ExpertAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertAdapter.ViewHolder {
        val view = ExpertListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertAdapter.ViewHolder, position: Int) {
        holder.binding.profileImg.clipToOutline = true
        holder.binding.nameTxt.text = ExpertList.get(position).name
        holder.binding.availableTimeTxt.text = ExpertList.get(position).availableTime
        holder.binding.phoneNumTxt.text = ExpertList.get(position).phoneNum

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExpertProfileActivity::class.java)
            intent.putExtra("expertUid", ExpertList.get(position).expertUid)
            PublicDatas().uidChange(ExpertList.get(position).expertUid)
            Log.d("dataChange", PublicDatas().ExpertUid)
            holder.itemView.context.startActivity(intent)



        }
    }

    override fun getItemCount(): Int {
        return ExpertList.size
    }

    class ViewHolder(val binding: ExpertListItemBinding): RecyclerView.ViewHolder(binding.root) {


    }



}