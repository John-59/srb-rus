package com.trainer.srb.rus.feature.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 4,
    exportSchema = true,
    entities = [
        SerbianLatinWord::class,
        SerbianCyrillicWord::class,
        RussianWord::class,
        SerbianRussianCrossRefTable::class,
        UnusedPredefined::class
    ]
)
@TypeConverters(
    DateTimeConverter::class,
    WordStatusConverter::class
)
abstract class InnerRepositoryDatabase: RoomDatabase() {

    abstract val dao: InnerRepositoryDao

    companion object {
        const val NAME = "inner_database"
    }
}