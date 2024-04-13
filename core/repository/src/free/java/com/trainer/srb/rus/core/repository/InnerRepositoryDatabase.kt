package com.trainer.srb.rus.core.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 7,
    exportSchema = true,
    entities = [
        SerbianLatinWord::class,
        SerbianCyrillicWord::class,
        RussianWord::class,
        SerbianRussianCrossRefTable::class,
        PredefinedStatus::class,
        RepeatAgain::class
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