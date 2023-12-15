package com.trainer.srb.rus.feature.repository

import androidx.room.Entity

@Entity(primaryKeys = ["srbLatWordId", "rusWordId"])
data class SerbianRussianCrossRefTable(
    val srbLatWordId: Long,
    val rusWordId: Long
)
