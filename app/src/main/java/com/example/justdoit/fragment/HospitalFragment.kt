package com.example.justdoit.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.justdoit.databinding.FragmentHospitalBinding

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

}