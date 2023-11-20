package com.example.justdoit.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityHospitalAddBinding
import com.example.justdoit.datas.HospitalInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HospitalAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHospitalAddBinding
    private val mStore: FirebaseFirestore = Firebase.firestore
    private val mAuth: FirebaseAuth = Firebase.auth
    private var hospitalUid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_JustDoIT_Hospital)
        binding = ActivityHospitalAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "병원 추가"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hospitalUid = mStore.collection("HospitalList").document().id

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressedDispatcher.onBackPressed()
            R.id.saveItem -> {
                var hospitalData = HospitalInfo(
                    hospitalUid!!,
                    binding.nameEdt.text.toString(),
                    binding.availableTimeEdt.text.toString(),
                    binding.hospitalNumEdt.text.toString(),
                    binding.addressEdt.text.toString(),
                    binding.detailEdt.text.toString()
                )
                mStore.collection("HospitalList").document(hospitalUid!!).set(hospitalData).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val defaultData = mapOf("review" to ArrayList<Map<String, String>>(), "score" to "0")
                        mStore.collection("HospitalList").document(hospitalUid!!).update(defaultData)
                        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}