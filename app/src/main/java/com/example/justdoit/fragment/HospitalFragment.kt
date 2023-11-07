package com.example.justdoit.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.HospitalAdapter
import com.example.justdoit.databinding.FragmentHospitalBinding
import com.example.justdoit.datas.HospitalList

class HospitalFragment : Fragment() {

    private var mBinding: FragmentHospitalBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHospitalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val hospitalList = arrayListOf(
            HospitalList("병원1", "010-1111-2222", "안녕하세요"),
            HospitalList("병원2", "010-1231-3434", "반갑습니다"),
            HospitalList("병원3", "010-5938-0099", "어서오세요"),
            HospitalList("병원4", "010-5678-4521", "노답이네")
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = HospitalAdapter(hospitalList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}