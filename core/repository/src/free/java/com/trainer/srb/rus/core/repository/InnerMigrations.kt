package com.trainer.srb.rus.core.repository

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

fun <T: RoomDatabase> RoomDatabase.Builder<T>.applyInnerMigrations(): RoomDatabase.Builder<T> {
    return this
        .addMigrations(MIGRATION_ASSETS_1_2)
        .addMigrations(MIGRATION_ASSETS_2_3)
        .addMigrations(MIGRATION_ASSETS_3_4)
        .addMigrations(MIGRATION_ASSETS_4_5)
        .addMigrations(MIGRATION_ASSETS_5_6)
}

private val MIGRATION_ASSETS_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
        with (db) {
            execSQL(
            "CREATE TABLE predefined_statuses_backup " +
                "(predefinedLatinId INTEGER NOT NULL, " +
                "status TEXT NOT NULL DEFAULT 'unknown', " +
                "status_time TEXT, " +
                "PRIMARY KEY(`predefinedLatinId`))"
            )
            execSQL("INSERT INTO predefined_statuses_backup SELECT predefinedLatinId, status, status_time FROM predefined_statuses")
            execSQL("DROP TABLE predefined_statuses")
            execSQL("ALTER TABLE predefined_statuses_backup RENAME TO predefined_statuses")
        }
    }
}

private val MIGRATION_ASSETS_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        with (db) {
            execSQL("ALTER TABLE unused_predefined RENAME TO predefined_statuses")
            execSQL("ALTER TABLE predefined_statuses ADD COLUMN status TEXT NOT NULL DEFAULT 'unknown'")
            execSQL("ALTER TABLE predefined_statuses ADD COLUMN status_time TEXT")
            execSQL("UPDATE predefined_statuses SET status = 'unused'")
        }
    }
}

private val MIGRATION_ASSETS_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        with (db) {
            execSQL("CREATE TABLE srb_lat_backup (id INTEGER NOT NULL, word TEXT NOT NULL, status TEXT NOT NULL, status_time TEXT, PRIMARY KEY(`id`))")
            execSQL("INSERT INTO srb_lat_backup SELECT id, word, status, status_time FROM srb_lat")
            execSQL("DROP TABLE srb_lat")
            execSQL("ALTER TABLE srb_lat_backup RENAME TO srb_lat")
            execSQL("UPDATE srb_lat SET status = LOWER(status)")
        }
    }
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