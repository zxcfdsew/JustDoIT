package com.example.justdoit.fragment

import android.content.Intent
import android.net.Uri
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
import com.google.firebase.storage.FirebaseStorage

class ExpertFragment : Fragment() {

    private lateinit var binding: FragmentExpertBinding
    private val mStore = Firebase.firestore
    private lateinit var mStorage: FirebaseStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExpertBinding.inflate(inflater, container, false)

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

        mStorage = FirebaseStorage.getInstance()

    }

    override fun onResume() {
        super.onResume()

        activity?.invalidateOptionsMenu()

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)

        var expertList = arrayListOf<ExpertInfo>()

        mStore.collection("ExpertList").get().addOnSuccessListener { result ->
            for (expertInfo in result) {

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

}
