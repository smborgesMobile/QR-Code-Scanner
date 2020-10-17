package com.br.smobile.com.result.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ResultModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val data: String,
    val text: String
)