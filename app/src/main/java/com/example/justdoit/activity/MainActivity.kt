package com.example.justdoit.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityMainBinding
import com.example.justdoit.fragment.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private var toolbarBackgroundColor: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = "일기"

        setFragment(DiaryFragment())
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.item_fragment_diary -> {
                    setFragment(DiaryFragment())
                    supportActionBar?.title = "일기"
                    expertVisibility(false)
                }
                R.id.item_fragment_community -> {
                    setFragment(CommunityFragment())
                    supportActionBar?.title = "커뮤니티"
                    expertVisibility(false)
                }
                R.id.item_fragment_help -> {
                    setFragment(ExpertFragment())
                    expertVisibility(true)
                    binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
                    binding.customSwitch.isSelected = false
                    supportActionBar?.title = "전문가"
                    toolbarBackgroundColor = false
                }
                R.id.item_fragment_setting -> {
                    setFragment(InfoFragment())
                    supportActionBar?.title = "내 정보"
                    expertVisibility(false)
                }
            }
            true
        }

        binding.customSwitch.setOnClickListener {
            if (binding.customSwitch.isSelected) {
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
                binding.customSwitch.isSelected = false
                supportActionBar?.title = "전문가"
                toolbarBackgroundColor = false
                setFragment(ExpertFragment())
            } else {
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sky))
                binding.customSwitch.isSelected = true
                toolbarBackgroundColor = true
                supportActionBar?.title = "병원"
                setFragment(HospitalFragment())
            }
        }

    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    private fun expertVisibility(state: Boolean) {
        if (state) {
            binding.customSwitch.visibility = View.VISIBLE
        } else {
            binding.customSwitch.visibility = View.INVISIBLE
            binding.toolbar.setBackgroundColor(Color.parseColor("#FFF9F6FF"))
            binding.customSwitch.isSelected = false
        }
    }

}