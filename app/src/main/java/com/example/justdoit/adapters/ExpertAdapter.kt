package com.example.justdoit.adapters

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.activity.ExpertProfileActivity
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.datas.ExpertList

class ExpertAdapter(val ExpertList: ArrayList<ExpertList>): RecyclerView.Adapter<ExpertAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertAdapter.ViewHolder {
        val view = ExpertListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpertAdapter.ViewHolder, position: Int) {
        holder.binding.profileImg.clipToOutline = true
        holder.binding.nameTxt.text = ExpertList.get(position).name
        holder.binding.availableTimeTxt.text = ExpertList.get(position).availableTime
        holder.binding.phoneNumTxt.text = ExpertList.get(position).phoneNumber

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ExpertProfileActivity::class.java)
            intent.putExtra("documentId", ExpertList.get(position).documentId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return ExpertList.size
    }

    class ViewHolder(val binding: ExpertListItemBinding): RecyclerView.ViewHolder(binding.root) {


    }



}