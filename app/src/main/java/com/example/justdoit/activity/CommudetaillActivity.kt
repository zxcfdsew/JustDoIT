package com.example.justdoit.activity

import android.content.Intent
import android.graphics.*
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.justdoit.R
import com.example.justdoit.adapters.CommentAdapter
import com.example.justdoit.databinding.ActivityCommudetaillBinding
import com.example.justdoit.datas.Comment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class CommudetaillActivity : AppCompatActivity() {

    private var mBinding: ActivityCommudetaillBinding? = null
    private val binding get() = mBinding!!
    private lateinit var mAuth: FirebaseAuth
    private val mStore = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCommudetaillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        val categoryTitle = getIntent().getStringExtra("category").toString()
        binding.toolbarTitle.text = categoryTitle

        Log.d("타이틀111", categoryTitle)

        mAuth = Firebase.auth

    }

    override fun onResume() {
        super.onResume()
        val uid = mAuth.currentUser!!.uid //현재 유저
        val documentUid = getIntent().getStringExtra("documentUid").toString()
        var commentlist = ArrayList<Comment>()
        Log.d("documentUid", documentUid)


        //게시글 상세에 내용, 댓글 보여주기
        mStore.collection("Community").document(documentUid).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Log.d("파이어베이스", "실행")
                    binding.titleTv.text = documentSnapshot.getString("title").toString()
                    binding.contentTv.text = documentSnapshot.getString("content").toString()
                    binding.timeTv.text =  getIntent().getStringExtra("time").toString()
                    val commentArray =
                        documentSnapshot.get("comments") as? ArrayList<HashMap<String, String>>
                    val heartCount = documentSnapshot.get("heartCount") as? ArrayList<String>
                    val heartSize = heartCount?.size ?: 0
                    if (heartCount != null) {
                        binding.heartCountTv.text = heartSize.toString()
                        for (data in heartCount) {
                            if (data == uid) {
                                binding.heart.isVisible = true
                                binding.blankheart.isGone = true
                            }
                        }
                    }


                    if (commentArray != null) {
                        for (dataMap in commentArray) {
                            var item = Comment(
                                dataMap["comment"] as String,
                                dataMap["nickname"] as String,
                                dataMap["posttime"] as String,
                                dataMap["commentId"] as String,
                                dataMap["writeUid"] as String
                            )
                            commentlist.add(item)
                        }
                        binding.commentCountTv.text = commentlist.size.toString()
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                        val dividerItemDecoration = DividerItemDecoration(
                            binding.recyclerView.context,
                            LinearLayoutManager(this).orientation
                        )
                        binding.recyclerView.addItemDecoration(dividerItemDecoration)
                        val commentAdapter = CommentAdapter(commentlist, documentUid, object:CommentAdapter.Delete{
                            override fun delete() {
                                binding.commentCountTv.text = commentlist.size.toString()
                            }

                        })
                        binding.recyclerView.adapter = commentAdapter
                        commentAdapter.notifyDataSetChanged()

                    }

                }
            }
        //공감 누르는 부분
        binding.blankheart.setOnClickListener {
            binding.heart.isVisible = true
            binding.blankheart.isGone = true
            binding.heartCountTv.text = (binding.heartCountTv.text.toString().toInt() + 1).toString()
            mStore.collection("Community").document(documentUid)
                .update("heartCount", FieldValue.arrayUnion(uid))

        }

        //공감 취소
        binding.heart.setOnClickListener {

            mStore.collection("Community").document(documentUid).get()
                .addOnSuccessListener { documentSnapshot ->
                    val heartCount = documentSnapshot.get("heartCount") as? ArrayList<String>
                    if (heartCount != null) {
                        heartCount.remove(uid)
                        val updates = hashMapOf<String, Any>(
                            "heartCount" to heartCount
                        )
                        mStore.collection("Community").document(documentUid).update(updates)
                            .addOnSuccessListener {
                                binding.blankheart.isVisible = true
                                binding.heart.isGone = true
                                binding.heartCountTv.text = (binding.heartCountTv.text.toString().toInt() - 1).toString()
                            }
                    }

                }
        }
            //댓글 작성하는 부분

            binding.commentAddTv.setOnClickListener {
                Log.d("작성","클릭")
                val commentId = mStore.collection("Community").document().id

                val currentTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    LocalDateTime.now().toString()
                } else {
                    TODO("VERSION.SDK_INT < O")
                }

                var nickName = ""

                mStore.collection("Accounts").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            val currentTime2 = System.currentTimeMillis()
                            var comment = Comment(
                                binding.commentAddEdt.text.toString(),
                                document["nickname"] as String,
                                currentTime2.toString(),
                                commentId,
                                uid
                            )
                            commentlist.add(comment)
                            Log.d("닉네임", nickName)

                            mStore.collection("Community").document(documentUid)
                                .update("comments", FieldValue.arrayUnion(comment))
                                .addOnSuccessListener {
                                    Toast.makeText(this, "댓글을 작성했습니다.", Toast.LENGTH_SHORT).show()
                                    binding.recyclerView.layoutManager =
                                        LinearLayoutManager(
                                            this,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                    binding.recyclerView.adapter = CommentAdapter(commentlist,documentUid,object:CommentAdapter.Delete{
                                        override fun delete() {
                                            binding.commentCountTv.text = commentlist.size.toString()
                                        }

                                    })
                                    val dividerItemDecoration = DividerItemDecoration(
                                        binding.recyclerView.context,
                                        LinearLayoutManager(this).orientation
                                    )
//                                    CommentAdapter(commentlist,documentUid).notifyDataSetChanged()
                                    binding.recyclerView.scrollToPosition(commentlist.size - 1)
                                    binding.recyclerView.addItemDecoration(dividerItemDecoration)
                                    binding.commentAddEdt.text = null
                                    binding.commentCountTv.text = commentlist.size.toString()
                                }
                        }
                    }

            }

    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

            val inflater = menuInflater
            val writeUid = getIntent().getStringExtra("uid").toString() //글 쓴 유저
            Log.d("글쓴 uid", writeUid)
            val currentUid = mAuth.currentUser!!.uid   //현재 접속유저
            Log.d("현재 접속 uid", currentUid)
            inflater.inflate(R.menu.menu_edit, menu)
            if (currentUid == writeUid) {
                Log.d("메뉴", "실행")
                menu?.findItem(R.id.edit)?.isVisible = true
                menu?.findItem(R.id.delete)?.isVisible = true
            } else {
                menu?.findItem(R.id.edit)?.isVisible = false
                menu?.findItem(R.id.delete)?.isVisible = false
                Log.d("메뉴", "안실행")
            }
            return true
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val documentUid = getIntent().getStringExtra("documentUid").toString()
            Log.d("documentUid", documentUid)
            //게시글 상세에 내용 보여주기
            when (item.itemId) {
                R.id.edit -> {
                    mStore.collection("Community").document(documentUid).get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                Log.d("파이어베이스", "실행")
                                var intent = Intent(this, CommunityAddActivity::class.java)
                                intent.putExtra(
                                    "editTitle",
                                    documentSnapshot.getString("title").toString()
                                )
                                intent.putExtra(
                                    "editContent",
                                    documentSnapshot.getString("content").toString()
                                )
                                intent.putExtra("editMode", true)
                                intent.putExtra("documentId", documentUid)
                                startActivity(intent)
                            }
                        }

                    return true
                }
                R.id.delete -> {
                    mStore.collection("Community").document(documentUid).delete()
                        .addOnSuccessListener {
                            Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    return true
                }
                else -> return false
            }
        }
    }
