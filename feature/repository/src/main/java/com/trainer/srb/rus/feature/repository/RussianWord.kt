package com.trainer.srb.rus.feature.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rus")
data class RussianWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val word: String = "",
)
