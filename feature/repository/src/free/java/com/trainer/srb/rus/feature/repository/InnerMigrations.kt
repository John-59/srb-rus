package com.trainer.srb.rus.feature.repository

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

fun <T: RoomDatabase> RoomDatabase.Builder<T>.applyInnerMigrations(): RoomDatabase.Builder<T> {
    return this
        .addMigrations(MIGRATION_ASSETS_1_2)
        .addMigrations(MIGRATION_ASSETS_2_3)
}

private val MIGRATION_ASSETS_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE srb_lat ADD COLUMN status TEXT NOT NULL DEFAULT 'UNKNOWN'")
        db.execSQL("ALTER TABLE srb_lat ADD COLUMN status_time TEXT")
    }
}

private val MIGRATION_ASSETS_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS `unused_predefined` " +
                "(`id` INTEGER NOT NULL DEFAULT 0, " +
                "`predefinedLatinId` INTEGER NOT NULL DEFAULT 0, " +
                "PRIMARY KEY(`id`))"
        db.execSQL(sql)
    }
}