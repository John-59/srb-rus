package com.trainer.srb.rus.feature.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unused_predefined")
data class UnusedPredefined(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val predefinedLatinId: Long
)
