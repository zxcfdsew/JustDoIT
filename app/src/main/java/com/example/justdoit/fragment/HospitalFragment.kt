package com.example.justdoit.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.R
import com.example.justdoit.activity.HospitalAddActivity
import com.example.justdoit.adapters.HospitalAdapter
import com.example.justdoit.databinding.FragmentHospitalBinding
import com.example.justdoit.datas.HospitalInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HospitalFragment : Fragment() {

    private var mBinding: FragmentHospitalBinding? = null
    private val binding get() = mBinding!!
    private val mStore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myInflater = inflater.cloneInContext(ContextThemeWrapper(requireActivity(), R.style.Theme_JustDoIT_Hospital))
        mBinding = FragmentHospitalBinding.inflate(myInflater, container, false)
        val menuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.add_hospital, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.addHospital -> {
                        val hospitalIntent = Intent(context, HospitalAddActivity::class.java)
                        startActivity(hospitalIntent)
                    }
                }
                return false
            }

        }, this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onResume() {
        super.onResume()

        activity?.invalidateOptionsMenu()

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)

        var hospitalList = arrayListOf<HospitalInfo>()

        mStore.collection("HospitalList").get().addOnSuccessListener { result ->
            for (hospitalInfo in result) {
                hospitalList.add(
                    HospitalInfo(
                        hospitalInfo.get("hospitalUid").toString(),
                        hospitalInfo.get("name").toString(),
                        hospitalInfo.get("availableTime").toString(),
                        hospitalInfo.get("hospitalNum").toString(),
                        hospitalInfo.get("address").toString(),
                        hospitalInfo.get("detail").toString()
                    )
                )
            }
            binding.recyclerView.adapter = HospitalAdapter(hospitalList)
        }
    }

}