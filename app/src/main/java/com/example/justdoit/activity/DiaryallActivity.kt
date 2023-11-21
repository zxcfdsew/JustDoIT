package com.example.justdoit.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justdoit.adapters.DiaryAdapter
import com.example.justdoit.databinding.ActivityDiaryallBinding
import com.example.justdoit.datas.Diary

class DiaryallActivity : AppCompatActivity() {
    private var mBinding:ActivityDiaryallBinding? = null
    private val binding get() = mBinding!!
    private var diarylist = mutableListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDiaryallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val pref = getSharedPreferences("Diary", Context.MODE_PRIVATE)
        val pref2 = getSharedPreferences("All", Context.MODE_PRIVATE)
        val diaries = ArrayList<Diary>()

        for(entry in pref2.all){
            diarylist.add(entry.key.toString().toInt())
        }
        Log.d("정렬전",diarylist.toString())

        diarylist.sort()
        Log.d("일기리스트 정렬",diarylist.toString())

        for (i in diarylist){
            for (entry in pref.all){
                val key = entry.key
                val value = entry.value as String
                val split = key.split("-")
                val date = split[0]
                val type = split[1]
                if (i == date.toInt()){
                    var diary = diaries.find { it.date == date }
                    if (diary == null) {
                        diary = Diary(date, "", "","")
                        diaries.add(diary)
                        Log.d("diary",diaries.toString())
                    }
                    if (type == "title") {
                        diary.title = value
                    } else if (type == "content") {
                        diary.content = value
                    }else if(type == "time"){
                        diary.time = value
                    }
                }
            }
        }
        Log.d("diary마지막",diaries.toString())

        Log.d("diary2",diaries.toString())
        // 리사이클러뷰에 레이아웃 매니저와 어댑터를 설정합니다.
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.recyclerView.adapter = DiaryAdapter(diaries)
        DiaryAdapter(diaries).notifyDataSetChanged()

        }
    }


