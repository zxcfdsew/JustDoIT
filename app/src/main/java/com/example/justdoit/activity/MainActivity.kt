package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityMainBinding
import com.example.justdoit.fragment.*

class MainActivity : AppCompatActivity() {

    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
//        뒤로가기 버튼
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setFragment(HomeFragment())
        binding.bottomNavigation.itemIconTintList = null
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item_fragment_home -> setFragment(HomeFragment())
                R.id.item_fragment_diary -> setFragment(DiaryFragment())
                R.id.item_fragment_community -> setFragment(CommunityFragment())
                R.id.item_fragment_search -> setFragment(SearchFragment(object : SearchFragment.isBackgroundColorChanged {
                    override fun onColorChanged(color: Int) {
                        binding.toolbar.setBackgroundColor(color)
                        Log.d("test listener", "success")
                    }
                }))
                R.id.item_fragment_setting -> setFragment(InfoFragment())
            }
            true
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

}