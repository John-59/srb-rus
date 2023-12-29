package com.trainer.srb.rus.feature.repository

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

fun <T: RoomDatabase> RoomDatabase.Builder<T>.applyInnerMigrations(): RoomDatabase.Builder<T> {
    return this.addMigrations(MIGRATION_ASSETS_1_2)
}

private val MIGRATION_ASSETS_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS `unused_predefined` " +
                "(`id` INTEGER NOT NULL DEFAULT 0, " +
                "`predefinedLatinId` INTEGER NOT NULL DEFAULT 0, " +
                "PRIMARY KEY(`id`))"
        db.execSQL(sql);
    }
}