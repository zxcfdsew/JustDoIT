package com.example.justdoit.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.R
import com.example.justdoit.activity.ExpertProfileActivity
import com.example.justdoit.databinding.FragmentExpertInfoBinding
import com.example.justdoit.datas.PublicDatas
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertInfoFragment(var Uid: String) : Fragment() {
    private var mBinding: FragmentExpertInfoBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentExpertInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = mStore.collection("ExpertList").document(Uid)
        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val data = task.result
                binding.introduceTxt.text = data.get("introduce").toString()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}