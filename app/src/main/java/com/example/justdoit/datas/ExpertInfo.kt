package com.example.justdoit.datas

import android.net.Uri

data class ExpertInfo(
    val expertUid: String,
    val name: String,
    val availableTime: String,
    val phoneNum: String,
    val introduce: String,
    val score: String = "0.0"
)