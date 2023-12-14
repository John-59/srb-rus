package com.trainer.srb.rus.core.repository

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class SerbianToRussianWord(
    @Embedded
    val serbianLat: SerbianLatinWord,
    @Relation(
        parentColumn = "id",
        entityColumn = "latId"
    )
    val serbianCyr: SerbianCyrillicWord,
    @Relation(
        parentColumn = "srbLatWordId",
        entityColumn = "rusWordId",
        associateBy = Junction(SerbianRussianCrossRefTable::class)
    )
    val russians: List<RussianWord>
)
