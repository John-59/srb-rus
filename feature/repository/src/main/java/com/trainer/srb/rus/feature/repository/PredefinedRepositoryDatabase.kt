package com.trainer.srb.rus.feature.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 6,
    exportSchema = true,
    entities = [
        SerbianLatinWord::class,
        SerbianCyrillicWord::class,
        RussianWord::class,
        SerbianRussianCrossRefTable::class
    ]
)
abstract class PredefinedRepositoryDatabase: RoomDatabase() {

    abstract val dao: PredefinedRepositoryDao

    companion object {
        const val NAME = "predefined_database"
    }
}