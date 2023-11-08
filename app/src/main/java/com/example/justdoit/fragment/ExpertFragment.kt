package com.example.justdoit.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.R
import com.example.justdoit.activity.ExpertAddActivity
import com.example.justdoit.adapters.ExpertAdapter
import com.example.justdoit.databinding.FragmentExpertBinding
import com.example.justdoit.datas.ExpertInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ExpertFragment : Fragment() {

    private var mBinding: FragmentExpertBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentExpertBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_expert, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.addExpert -> {
                        val intent = Intent(context, ExpertAddActivity::class.java)
                        startActivity(intent)
                    }
                }
                return false
            }

        }, this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)

        var expertList = arrayListOf<ExpertInfo>(
        )

        mStore.collection("ExpertList").get().addOnSuccessListener { result ->
            for (expertInfo in result) {
                Log.d("firestore", expertInfo.get("expertUid").toString())
                expertList.add(
                    ExpertInfo(
                        expertInfo.get("expertUid").toString(),
                        expertInfo.get("name").toString(),
                        expertInfo.get("availableTime").toString(),
                        expertInfo.get("phoneNum").toString(),
                        expertInfo.get("introduce").toString()
                    )
                )
            }
            binding.recyclerView.adapter = ExpertAdapter(expertList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()
        activity?.invalidateOptionsMenu()
    }

}