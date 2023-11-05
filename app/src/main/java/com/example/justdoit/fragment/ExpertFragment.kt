package com.example.justdoit.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.R
import com.example.justdoit.adapters.ExpertAdapter
import com.example.justdoit.databinding.FragmentExpertBinding
import com.example.justdoit.datas.ExpertList

class ExpertFragment : Fragment() {

    private var mBinding: FragmentExpertBinding? = null
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
        mBinding = FragmentExpertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ExpertList = arrayListOf(
            ExpertList("타이틀1", "이름1", "010-2222-1111"),
            ExpertList("타이틀2", "이름2", "010-3333-4444"),
            ExpertList("타이틀3", "이름3", "010-6666-5555")
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = ExpertAdapter(ExpertList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}