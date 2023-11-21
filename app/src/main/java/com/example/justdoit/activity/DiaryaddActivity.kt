package com.example.justdoit.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import com.example.justdoit.databinding.ActivityDiaryaddBinding
import java.text.SimpleDateFormat

class DiaryaddActivity : AppCompatActivity() {
    private var mBinding: ActivityDiaryaddBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDiaryaddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val day = intent.getStringExtra("day").toString()
        binding.toolbarTitle.text = day
        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.addTv.text = "작성"

        val editDay = intent.getStringExtra("editDay").toString()
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val isEditMode = intent.getBooleanExtra("editMode", false)
        Log.d("처음day", day)

        if (isEditMode) {
            binding.addTv.text = "수정"
            binding.toolbarTitle.text = editDay
            binding.titleEdt.setText(title)
            binding.contentEdt.setText(content)
            Log.d("수정됨",isEditMode.toString())
        }
        binding.addTv.setOnClickListener {
            if (isEditMode){
                addDiary(editDay)
            }else{
                addDiary(day)

            }

        }
    }



    private fun addDiary(day: String) {
        val title = binding.titleEdt.text.toString()
        val content = binding.contentEdt.text.toString()
        val time =  System.currentTimeMillis()
        var dateFormat = SimpleDateFormat("mm:ss")
        var getTime = dateFormat.format(time)
        val pref = getSharedPreferences("Diary", Context.MODE_PRIVATE)
        val pref2 = getSharedPreferences("All", Context.MODE_PRIVATE)
        if (title.trim().isNotEmpty() && content.trim().isNotEmpty()) { //비어있거나 공백 아닌 경우
            Log.d("공백없을경우day", day)
            pref.edit {
                putString("$day-title", title)
                putString("$day-content", content)
                putString("$day-time", getTime)
                apply() //비동기처리, commit는 동기처리
            }
            pref2.edit().putString(day,"저장").apply()
            Log.d("작성누름", "저장완료")
            finish()
        } else {
            Toast.makeText(this, "제목과 내용을 작성해주세요", Toast.LENGTH_SHORT).show()
        }
    }
}