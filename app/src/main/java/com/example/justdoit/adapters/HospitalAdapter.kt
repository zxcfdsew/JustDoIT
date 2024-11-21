package com.example.justdoit.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.justdoit.R
import com.example.justdoit.activity.HospitalDetailActivity
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.databinding.HospitalListItemBinding
import com.example.justdoit.datas.HospitalInfo
import com.google.firebase.storage.FirebaseStorage

class HospitalAdapter(val HospitalList: ArrayList<HospitalInfo>) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    private val mStorage = FirebaseStorage.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAdapter.ViewHolder {
        val view = HospitalListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view).also {viewHolder ->
            viewHolder.itemView.setOnClickListener {
                val intent = Intent(viewHolder.itemView.context, HospitalDetailActivity::class.java)
                intent.putExtra("hospitalUid", HospitalList.get(viewHolder.adapterPosition).hospitalUid)
                viewHolder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun onBindViewHolder(holder: HospitalAdapter.ViewHolder, position: Int) {
        holder.bind(HospitalList[position])
    }

    override fun getItemCount(): Int {
        return HospitalList.size
    }

    inner class ViewHolder(private val binding: HospitalListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hospitalList: HospitalInfo) {

            var imageName = "Hospital_" + hospitalList.hospitalUid
            val storageRef = mStorage.reference.child("profileImg").child(imageName)

            storageRef.downloadUrl.addOnSuccessListener { uri ->
                Glide.with(binding.root.context).load(uri).into(binding.profileImg)
            }.addOnFailureListener {
                binding.profileImg.setImageResource(R.drawable.ic_launcher_background)
            }

            binding.profileImg.clipToOutline = true
            binding.nameTxt.text = hospitalList.name
            binding.availableTimeTxt.text = hospitalList.availableTime
            binding.phoneNumTxt.text = hospitalList.hospitalNum
            binding.addressTxt.text = hospitalList.address
        }
    }
}