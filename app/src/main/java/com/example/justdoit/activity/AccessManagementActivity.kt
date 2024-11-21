package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.justdoit.databinding.ActivityAccessManagementBinding
import java.text.SimpleDateFormat

class AccessManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccessManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccessManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var curTimeMillis = System.currentTimeMillis();
        var timeFomat = SimpleDateFormat("a hh시 mm분")
        var curTime = timeFomat.format(curTimeMillis)

//        binding.currentTime.text = curTime

    }
}