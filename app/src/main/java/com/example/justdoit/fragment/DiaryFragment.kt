package com.example.justdoit.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.justdoit.R
import com.example.justdoit.activity.DiaryaddActivity
import com.example.justdoit.activity.DiaryallActivity
import com.example.justdoit.activity.DiarydetailActivity
import com.example.justdoit.databinding.FragmentDiaryBinding
import java.util.*

class DiaryFragment : Fragment() {

    private var mBinding: FragmentDiaryBinding? = null
    private val binding get() = mBinding!!
    private var day = "" //초기화 먼저 하는 이유: 초기화된 블록내에서 만 사용가능하기 때문에 먼저 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDiaryBinding.inflate(layoutInflater, container, false)

        if (day.isEmpty()) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = binding.calendarView.date
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            day = String.format("%d%d%d", year, month, dayOfMonth)
            Log.d("처음시작 날짜", day)
        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    @SuppressLint("SuspiciousIndentation")
    override fun onResume() {
        super.onResume()


        loadDiary(day)

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            day = String.format("%d%d%d", year, month + 1, dayOfMonth)
            Log.d("다시시작", day)
            loadDiary(day)
        }

        binding.addFab.setOnClickListener {

            val pref = this.activity?.getSharedPreferences("Diary", Context.MODE_PRIVATE)
            if (pref!!.contains("$day-title")) {
                Toast.makeText(context, "일기는 1개만 작성이 가능합니다.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(context, DiaryaddActivity::class.java)
                intent.putExtra("day", day)
                startActivity(intent)
            }


        }

        binding.summaryLayout.setOnClickListener {
            var intent = Intent(context, DiarydetailActivity::class.java)
            intent.putExtra("day", day)
            Log.d("상세", day)
            startActivity(intent)
        }
    }

    private fun loadDiary(day: String) {

        val activity = this.activity
        if (activity != null) {
            val pref = activity.getSharedPreferences("Diary", Context.MODE_PRIVATE)
            if (pref.contains("$day-title")) {
                val title = pref.getString("$day-title", "제목 없음")
                val content = pref.getString("$day-content", "내용 없음")
                binding.summaryLayout.isVisible = true
                binding.titleTv.text = title
                binding.contentTv.text = content
            } else {
                binding.summaryLayout.isVisible = false
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.diary_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.show -> {
                var intent = Intent(context, DiaryallActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}
