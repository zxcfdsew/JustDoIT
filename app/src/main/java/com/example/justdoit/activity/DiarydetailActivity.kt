package com.example.justdoit.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityDiarydetailBinding

class DiarydetailActivity : AppCompatActivity() {
    private var mBinding: ActivityDiarydetailBinding? = null
    private val binding get() = mBinding!!
    private var day = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDiarydetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        day = intent.getStringExtra("day").toString()
        Log.d("상세에서 받은 날짜", day)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
       binding.toolbarTitle.text = day
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        val pref = getSharedPreferences("Diary", Context.MODE_PRIVATE)
        binding.titleTv.text = pref.getString("$day-title", "제목")
        binding.contentTv.text = pref.getString("$day-content", "내용")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    //액션버튼 클릭 했을 때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val pref = getSharedPreferences("Diary", Context.MODE_PRIVATE)
        val pref2 = getSharedPreferences("All", Context.MODE_PRIVATE)
        when (item?.itemId) {
            R.id.edit -> {
                var intent = Intent(this, DiaryaddActivity::class.java)
                intent.putExtra("title", pref.getString("$day-title", "제목"))
                intent.putExtra("content", pref.getString("$day-content", "내용"))
                intent.putExtra("editMode", true)
                intent.putExtra("editDay", day)
                startActivity(intent)
                return true
            }
            R.id.delete -> {
                val editor = pref.edit()
                editor.remove("$day-title")
                editor.remove("$day-content")
                editor.remove("$day-time")
                editor.apply()
                Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                val editor2 = pref2.edit()
                editor2.remove(day)
                editor2.apply()
                finish()
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }
}

