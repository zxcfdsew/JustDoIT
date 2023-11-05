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
import com.example.justdoit.datas.ExpertList

class ExpertAdapter(val ExpertList: ArrayList<ExpertList>): RecyclerView.Adapter<ExpertAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpertAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expert_list_item, parent, false)
        return ViewHolder(view).apply {
            itemView.setOnClickListener {
                val intent = Intent(parent.context, ExpertProfileActivity::class.java)
                intent.run {
                    parent.context.startActivity(intent) }
            }
        }
    }

    override fun onBindViewHolder(holder: ExpertAdapter.ViewHolder, position: Int) {
        holder.profileImg.clipToOutline = true

        holder.title.text = ExpertList.get(position).title
        holder.name.text = ExpertList.get(position).name
        holder.phoneNumber.text = ExpertList.get(position).phoneNumber
    }

    override fun getItemCount(): Int {
        return ExpertList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleTxt)
        val name = itemView.findViewById<TextView>(R.id.nameTxt)
        val phoneNumber = itemView.findViewById<TextView>(R.id.phoneNumTxt)
        val profileImg = itemView.findViewById<ImageView>(R.id.profileImg)

    }



}