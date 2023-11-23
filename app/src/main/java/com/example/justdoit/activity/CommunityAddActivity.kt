package com.example.justdoit.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.justdoit.R
import com.example.justdoit.databinding.ActivityCommunityAddBinding
import com.example.justdoit.datas.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.Timestamp
import java.time.LocalDateTime

class CommunityAddActivity : AppCompatActivity() {

    private var mBinding: ActivityCommunityAddBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mAuth: FirebaseAuth
    private val mStore = Firebase.firestore
    private val activityLauncher = openActivityResultLauncher()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommunityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        mAuth = Firebase.auth
        Log.d("uid", mAuth.toString())

    }

    private fun openActivityResultLauncher(): ActivityResultLauncher<Intent> {
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    binding.categoryTv.text = result.data?.getStringExtra("category")
                } else {
                }
            }
        return resultLauncher
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        val isEditMode = intent.getBooleanExtra("editMode", false)
        Log.d("수정여부", isEditMode.toString())
        if (isEditMode) {
            val title = intent.getStringExtra("editTitle").toString()
            val content = intent.getStringExtra("editContent").toString()
            val documentId = intent.getStringExtra("documentId").toString()
            binding.titleEdt.setText(title)
            binding.contentEdt.setText(content)
            binding.addTv.text = "수정"
            binding.categoryTv.setTextColor(ContextCompat.getColor(applicationContext!!, R.color.gray)
            )

            binding.addTv.setOnClickListener {
                val editTitle = binding.titleEdt.text.toString()
                val editContent = binding.contentEdt.text.toString()
                if (editTitle.isNotEmpty() && editContent.isNotEmpty()) {
                    val updatedData: Map<String, Any> = hashMapOf(
                        "title" to editTitle,
                        "content" to  editContent
                    )
                    mStore.collection("Community")
                        .document(documentId)
                        .update(updatedData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "게시글이 수정되었습니다.", Toast.LENGTH_SHORT).show()
                            finish() // 수정 완료 후 액티비티 종료
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "수정 실패.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }

            }
        } else {

            binding.categoryTv.setOnClickListener {
                val intent = Intent(this, CategoryActivity::class.java)
                activityLauncher.launch(intent)
            }
            val documentUid = mStore.collection("Community").document().id
            binding.addTv.setOnClickListener {
                val title = binding.titleEdt.text.toString()
                val content = binding.contentEdt.text.toString()
                val category = binding.categoryTv.text.toString()
                val current = LocalDateTime.now().toString()
                val uid = mAuth.currentUser!!.uid

                Log.d("uid", uid)
                if (title.isEmpty() || content.isEmpty() || binding.categoryTv.text.toString() == "카테고리 선택"
                ) {
                    Toast.makeText(this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show()
                } else {

                    var communitylist = hashMapOf(
                        "title" to title,
                        "content" to content,
                        "category" to category,
                        "time" to current,
                        "uid" to uid,
                        "documentUid" to documentUid
                    )
                    Log.d("community", communitylist.toString())
                    //파이어스토어에 저장
                    mStore.collection("Community").document(documentUid).set(communitylist)
                        .addOnSuccessListener {
                            Toast.makeText(this, "작성되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                }


            }
        }

    }

}