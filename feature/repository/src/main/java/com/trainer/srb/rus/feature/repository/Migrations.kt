package com.trainer.srb.rus.feature.repository

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

fun <T: RoomDatabase> RoomDatabase.Builder<T>.applyMigrations(): RoomDatabase.Builder<T> {
    return this
        .addMigrations(MIGRATION_ASSETS_1_2)
        .addMigrations(MIGRATION_ASSETS_2_3)
        .addMigrations(MIGRATION_ASSETS_3_4)
        .addMigrations(MIGRATION_ASSETS_4_5)
        .addMigrations(MIGRATION_ASSETS_5_6)
}

val MIGRATION_ASSETS_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

val MIGRATION_ASSETS_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

val MIGRATION_ASSETS_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

val MIGRATION_ASSETS_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE srb_lat ADD COLUMN unused INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_ASSETS_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}