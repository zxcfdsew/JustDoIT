package com.example.justdoit.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.example.justdoit.R
import com.example.justdoit.activity.*
import com.example.justdoit.databinding.FragmentInfoBinding
import com.example.justdoit.datas.Comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InfoFragment : Fragment() {

    private var mBinding : FragmentInfoBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mAuth: FirebaseAuth
    private val mStore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentInfoBinding.inflate(inflater, container, false)
        mAuth = Firebase.auth
        val uid = mAuth.currentUser!!.uid
        mStore.collection("Accounts").document(uid).get().addOnSuccessListener { document ->
            if (document != null) {
                binding.nicknameTv.text = document["nickname"] as String
            }
        }

        binding.nicknameTv.setOnClickListener {
            val AccessIntent = Intent(activity, AccessManagementActivity::class.java)
            startActivity(AccessIntent)
        }

        binding.mypostTv.setOnClickListener {
            val intent = Intent(context, MypostActivity::class.java)
            startActivity(intent)
        }
        binding.mycommentTv.setOnClickListener {
            val intent = Intent(context, MycommentActivity::class.java)
            startActivity(intent)
        }
        binding.reviewTv.setOnClickListener {
            val intent = Intent(context, MyreviewActivity::class.java)
            startActivity(intent)
        }
        binding.heartTv.setOnClickListener {
            val intent = Intent(context, MyheartActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}