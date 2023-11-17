package com.example.justdoit.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityMainBinding
import com.example.justdoit.fragment.*

class MainActivity : AppCompatActivity() {

    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!
    private var toolbarBackgroundColor: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = "홈"
//        뒤로가기 버튼
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setFragment(HomeFragment())
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_fragment_home -> {
                    setFragment(HomeFragment())
                    supportActionBar?.title = "홈"
                    expertVisibility(false)
                }
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
                    supportActionBar?.title = "help"
                    expertVisibility(true)
                    binding.toolbar.setBackgroundColor(Color.parseColor("#FFF9F6FF"))
                    binding.customSwitch.isSelected = false
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
                binding.toolbar.setBackgroundColor(Color.parseColor("#FFF9F6FF")) // light_purple
                binding.customSwitch.isSelected = false
                toolbarBackgroundColor = false
                setFragment(ExpertFragment())
            } else {
                binding.toolbar.setBackgroundColor(Color.parseColor("#FFF5F6FF"))  // light_sky
                binding.customSwitch.isSelected = true
                toolbarBackgroundColor = true
                setFragment(HospitalFragment())
            }
        }

    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.custom_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.test1 -> {
                Toast.makeText(this, "test1", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.test2 -> {
                Toast.makeText(this, "test2", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return false
        }
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