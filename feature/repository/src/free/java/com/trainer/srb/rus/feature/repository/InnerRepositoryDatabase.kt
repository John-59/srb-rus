package com.trainer.srb.rus.feature.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 2,
    exportSchema = true,
    entities = [
        SerbianLatinWord::class,
        SerbianCyrillicWord::class,
        RussianWord::class,
        SerbianRussianCrossRefTable::class,
        UnusedPredefined::class
    ]
)
abstract class InnerRepositoryDatabase: RoomDatabase() {

    abstract val dao: InnerRepositoryDao

    companion object {
        const val NAME = "inner_database"
    }
}