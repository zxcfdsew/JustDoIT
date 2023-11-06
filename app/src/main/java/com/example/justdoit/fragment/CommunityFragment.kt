package com.example.justdoit.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.R
import com.example.justdoit.activity.CategoryActivity
import com.example.justdoit.activity.CommunityAddActivity
import com.example.justdoit.adapters.ViewPagerAdapter
import com.example.justdoit.databinding.FragmentCommunityBinding
import com.google.android.material.tabs.TabLayoutMediator

class CommunityFragment : Fragment() {

    private var mBinding : FragmentCommunityBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mViewPagerAdapter : ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCommunityBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = ViewPagerAdapter(this, 10)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position){
                0 -> tab.text = "메인"
                1 -> tab.text = "일반 고민"
                2 -> tab.text = "취업/진로"
                3 -> tab.text = "직장"
                4 -> tab.text = "연애"
                5 -> tab.text = "성추행"
                6 -> tab.text = "결혼/육아"
                7 -> tab.text = "대인관계"
                8 -> tab.text = "외모"
                9 -> tab.text = "가족"
            }
        }.attach()
        binding.fab.setOnClickListener{
            val intent = Intent(context, CommunityAddActivity::class.java)
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