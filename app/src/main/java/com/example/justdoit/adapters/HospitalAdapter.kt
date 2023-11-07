package com.example.justdoit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.databinding.ExpertListItemBinding
import com.example.justdoit.datas.HospitalList

class HospitalAdapter(val HospitalList: ArrayList<HospitalList>) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalAdapter.ViewHolder {
        val view = ExpertListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view).apply {
            itemView.setOnClickListener {
                Toast.makeText(parent.context, adapterPosition.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBindViewHolder(holder: HospitalAdapter.ViewHolder, position: Int) {
        holder.binding.profileImg.clipToOutline = true
        holder.binding.nameTxt.text = HospitalList.get(position).name

    }

    override fun getItemCount(): Int {
        return HospitalList.size
    }

    class ViewHolder(val binding: ExpertListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}