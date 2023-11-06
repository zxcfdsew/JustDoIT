package com.example.justdoit.activity

import android.content.Context
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityDiaryallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = getSharedPreferences("Diary", Context.MODE_PRIVATE)
        val diaries = ArrayList<Diary>()


        val qq = pref.all
        Log.d("qq",qq.toString())
        val bb = pref.all.entries
        Log.d("bb",bb.toString())
        // 모든 키-값 쌍을 불러옵니다.
        for (entry in pref.all.entries) {
            val key = entry.key
            Log.d("키값",key)
            val value = entry.value as String
            Log.d("밸류값",value)

            // 키에서 날짜와 title/content를 분리합니다.
            val split = key.split("-")
            Log.d("split",split.toString())
            val date = split[0]
            Log.d("date",date)
            val type = split[1]
            Log.d("type",type)

            // 적절한 Diary 객체를 찾거나 새로 만듭니다.
            var diary = diaries.find { it.date == date }
            if (diary == null) {
                diary = Diary(date, "", "")
                diaries.add(diary)
            }

            // 제목과 내용을 설정합니다.
            if (type == "title") {
                diary.title = value
            } else if (type == "content") {
                diary.content = value
            }
            Log.d("실행중ㅅ 배열작업",diaries.toString())
        }
        Log.d("마지막 배열작업",diaries.toString())

        // 리사이클러뷰에 레이아웃 매니저와 어댑터를 설정합니다.
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = DiaryAdapter(diaries)
    }

    }
