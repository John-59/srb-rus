package com.trainer.srb.rus.core.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "srb_cyr")
data class SerbianCyrillicWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val word: String = "",
    val latId: Long
)
