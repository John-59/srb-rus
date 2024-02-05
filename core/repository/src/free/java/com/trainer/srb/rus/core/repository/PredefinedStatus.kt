package com.trainer.srb.rus.core.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.trainer.srb.rus.core.repository.WordStatus
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "predefined_statuses")
data class PredefinedStatus(
    @PrimaryKey(autoGenerate = false)
    val predefinedLatinId: Long,
    val status: WordStatus,
    @ColumnInfo(name = "status_time")
    val statusDateTime: LocalDateTime?
)
