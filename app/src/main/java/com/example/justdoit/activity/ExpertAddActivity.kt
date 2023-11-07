package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityExpertAddBinding

class ExpertAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExpertAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpertAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

//        setSupportActionBar(toolbar)

    }
}