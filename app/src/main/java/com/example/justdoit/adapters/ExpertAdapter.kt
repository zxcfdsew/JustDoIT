package com.example.justdoit.adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justdoit.R
import com.example.justdoit.activity.ExpertProfileActivity
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.datas.ExpertInfo
import com.google.firebase.storage.FirebaseStorage

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

    inner class ViewHolder(private val binding: ExpertListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val mStorage = FirebaseStorage.getInstance()

        fun bind(expertInfo: ExpertInfo) {
            var imageName = "Expert_" + expertInfo.expertUid
            val storageRef = mStorage.reference.child("profileImg").child(imageName)

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(binding.root.context).load(uri).into(binding.profileImg)
            }.addOnFailureListener {
                binding.profileImg.setImageResource(R.drawable.ic_launcher_background)
            }

            binding.profileImg.clipToOutline = true
            binding.nameTxt.text = expertInfo.name
            binding.availableTimeTxt.text = expertInfo.availableTime
            binding.phoneNumTxt.text = expertInfo.phoneNum
        }

    }

}