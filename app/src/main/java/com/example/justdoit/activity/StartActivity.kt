package com.example.justdoit.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.justdoit.databinding.ActivityStartBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class StartActivity : AppCompatActivity() {

    private var mBinding: ActivityStartBinding? = null
    private val binding get() = mBinding!!

    private lateinit var mAuth: FirebaseAuth
    private val mStore = Firebase.firestore
    private var TAG = "testLogin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "시작하기"

        mAuth = Firebase.auth
        autoLogin()
        changeStartBtnState(false)

        binding.nicknameEdt.addTextChangedListener {
            changeStartBtnState(false)
        }

        binding.startBtnActivate.setOnClickListener {
            addNewAccount(binding.nicknameEdt.text.toString())
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }



        binding.nicknameCheckBtn.setOnClickListener {
            val userNicknames = arrayListOf<String>()
            Log.d(TAG, binding.nicknameEdt.text.toString())
            mStore.collection("Accounts")
                .whereEqualTo("nickname", binding.nicknameEdt.text.toString())
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        userNicknames.add(document.data.toString())
                    }
                    Log.d(TAG, userNicknames.toString())
                    if (userNicknames.size == 0) {
                        changeStartBtnState(true)
                        Toast.makeText(this, "사용 가능한 닉네임 입니다", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "이미 존재하는 닉네임입니다. 새로 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    private fun autoLogin() {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            intent = Intent(this, MainActivity::class.java)
            Log.d(TAG, "$currentUser + Autologin success")
            startActivity(intent)
            finish()
        }

    }

    private fun addNewAccount(nickname: String) {
        mAuth.signInAnonymously().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userUid = mAuth.currentUser!!.uid
                var datas = mapOf<String, String>("nickname" to nickname, "uid" to userUid)
                mStore.collection("Accounts").document(userUid).set(datas)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "new Account Created")
                        }
                    }
            }
        }

    }

    private fun changeStartBtnState(isActivated: Boolean) {
        if (isActivated) {
            binding.startBtnActivate.visibility = View.VISIBLE
            binding.startBtnDeactivate.visibility = View.INVISIBLE
        } else {
            binding.startBtnActivate.visibility = View.INVISIBLE
            binding.startBtnDeactivate.visibility = View.VISIBLE
        }
    }
}