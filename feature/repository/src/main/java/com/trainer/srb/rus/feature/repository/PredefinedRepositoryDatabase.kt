package com.trainer.srb.rus.feature.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 16,
    exportSchema = true,
    entities = [
        SerbianLatinWord::class,
        SerbianCyrillicWord::class,
        RussianWord::class,
        SerbianRussianCrossRefTable::class
    ]
)
@TypeConverters(
    DateTimeConverter::class,
    WordStatusConverter::class
)
abstract class PredefinedRepositoryDatabase: RoomDatabase() {

    abstract val dao: PredefinedRepositoryDao

    companion object {
        const val NAME = "predefined_database"
    }
}