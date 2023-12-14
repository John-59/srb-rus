package com.trainer.srb.rus.core.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "srb_lat")
data class SerbianLatinWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val word: String = "",
)
