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
    val serbianCyr: SerbianCyrillicWord?,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = SerbianRussianCrossRefTable::class,
            parentColumn = "srbLatWordId",
            entityColumn = "rusWordId"
        )
    )
    val russians: List<RussianWord>
)
