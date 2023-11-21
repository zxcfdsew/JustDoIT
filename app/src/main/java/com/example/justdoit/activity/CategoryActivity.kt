package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private var mBinding:ActivityCategoryBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "카테고리 선택"
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        binding.chip1.setOnClickListener {
            intent("일반고민")
        }
        binding.chip2.setOnClickListener {
            intent("취업/진로")
        }
        binding.chip3.setOnClickListener {
            intent("직장")
        }
        binding.chip4.setOnClickListener {
            intent("연애")
        }
        binding.chip5.setOnClickListener {
            intent("성추행")
        }
        binding.chip6.setOnClickListener {
            intent("결혼/육아")
        }
        binding.chip7.setOnClickListener {
            intent("대인관계")
        }
        binding.chip8.setOnClickListener {
            intent("외모")
        }
        binding.chip9.setOnClickListener {
            intent("가족")
        }

    }

    private fun intent(category:String){
        val intent = Intent()
        intent.putExtra("category", category)
        setResult(RESULT_OK, intent)
        finish()
    }
}