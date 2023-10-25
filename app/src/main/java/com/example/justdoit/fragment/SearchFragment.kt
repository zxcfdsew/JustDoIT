package com.example.justdoit.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.justdoit.databinding.FragmentSearchBinding

class SearchFragment(private val listener : isBackgroundColorChanged) : Fragment() {

    private var mBinding : FragmentSearchBinding? = null
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
        mBinding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.customSwitch.setOnClickListener {
            if (binding.customSwitch.isSelected) {
//                binding.linearLayout.setBackgroundColor(Color.parseColor("#FFF5F6FF"))    // light_sky
                binding.linearLayout.setBackgroundColor(Color.parseColor("#FFFFFF00"))
                binding.customSwitch.isSelected = false
                listener.onColorChanged(Color.parseColor("#FFFFFF00"))    // yellow
            } else {
                binding.linearLayout.setBackgroundColor(Color.parseColor("#FFF9F6FF"))    // light_purple
                binding.customSwitch.isSelected = true
                listener.onColorChanged(Color.parseColor("#FFF9F6FF"))
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    interface isBackgroundColorChanged {
        fun onColorChanged(color: Int)

    }

}