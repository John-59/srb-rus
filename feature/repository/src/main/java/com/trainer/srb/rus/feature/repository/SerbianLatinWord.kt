package com.trainer.srb.rus.feature.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "srb_lat")
data class SerbianLatinWord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val word: String = "",
    val status: WordStatus,
    @ColumnInfo(name = "status_time")
    val statusDateTime: LocalDateTime?
)
