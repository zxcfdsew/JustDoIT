package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityAddReviewBinding

class AddReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val getintent = intent
        val from = getintent.getStringExtra("from")
        setTheme(R.style.Theme_JustDoIT_Hospital)
        if (from.equals("expert")) {
            setTheme(R.style.Theme_JustDoIT)
        } else if (from.equals("hospital")) {
            setTheme(R.style.Theme_JustDoIT_Hospital)
        }
        binding = ActivityAddReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (from.equals("expert")) {
            binding.topLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_purple))
        } else if (from.equals("hospital")) {
            binding.topLinearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_sky))
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.saveItem -> Toast.makeText(this, "저장 클릭됨", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

}