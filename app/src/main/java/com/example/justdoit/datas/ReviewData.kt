package com.example.justdoit.datas

data class ReviewData(
    val reviewId: String,
    val nickname: String,
    val star: Float,
    val detail: String,
    val documentId: String,
    val writtedTime: String,
    val sorting: Long
)