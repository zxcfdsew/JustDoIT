package com.example.justdoit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.R
import com.example.justdoit.databinding.FragmentHospitalInfoBinding
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HospitalInfoFragment(private val hospitalId: String) : Fragment() {

    private var mBinding: FragmentHospitalInfoBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHospitalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = mStore.collection("HospitalList").document(hospitalId)
        db.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val data = task.result
                binding.introduceTxt.text = data.get("detail").toString()

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}