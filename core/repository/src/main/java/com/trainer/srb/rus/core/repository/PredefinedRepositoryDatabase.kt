package com.trainer.srb.rus.core.repository

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    exportSchema = false,
    entities = [
        SerbianLatinWord::class,
        SerbianCyrillicWord::class,
        RussianWord::class,
        SerbianRussianCrossRefTable::class
    ]
)
abstract class PredefinedRepositoryDatabase: RoomDatabase() {

    abstract val predefinedRepositoryDao: PredefinedRepositoryDao

    companion object {
        const val NAME = "predefined_database"
    }
}