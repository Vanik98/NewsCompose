package com.example.news.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.net.response.Result

@Entity
data class ResultLocal(
    @PrimaryKey(autoGenerate = true)
    val idResult: Int,
    @Embedded
    var result: Result,
    var isSave: Boolean
)