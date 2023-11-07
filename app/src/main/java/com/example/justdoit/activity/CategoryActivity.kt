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

        var category = ""
        binding.chipGroup.setOnCheckedChangeListener { group, checkedIds ->
            val intent = Intent(this, CommunityAddActivity::class.java)
            Log.d("test", "Click: $checkedIds")
            when(checkedIds){
             R.id.chip1 ->{
                    category = "일반고민"
                 Log.d("test", "Click: $checkedIds")
                    intent.putExtra("category","일반고민")
                    startActivity(intent)
                }
                R.id.chip2 ->{
                    category = "취업"
                    intent.putExtra("category","취업")
                    finish()
                }
                R.id.chip3 ->{
                    category = "진로"
                    intent.putExtra("category","진로")
                    finish()
                }
                R.id.chip4 ->{
                    category = "상당"
                    intent.putExtra("category","상당")
                    finish()
                }
                R.id.chip5 ->{
                    category = "5"
                    intent.putExtra("category","5")
                    finish()
                }
                R.id.chip6 ->{
                    category = "6"
                    intent.putExtra("category","6")
                    finish()
                }

            }
            if (intent.hasExtra("category")) {
                startActivity(intent)
            }
        }
        binding.chip1.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                val intent = Intent(this, CommunityAddActivity::class.java)
                intent.putExtra("category","일반고민")
                startActivity(intent)
            }
        }
    }
}