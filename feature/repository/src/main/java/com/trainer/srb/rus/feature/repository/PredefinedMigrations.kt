package com.trainer.srb.rus.feature.repository

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

fun <T: RoomDatabase> RoomDatabase.Builder<T>.applyPredefinedMigrations(): RoomDatabase.Builder<T> {
    return this
        .addMigrations(MIGRATION_ASSETS_1_2)
        .addMigrations(MIGRATION_ASSETS_2_3)
        .addMigrations(MIGRATION_ASSETS_3_4)
        .addMigrations(MIGRATION_ASSETS_4_5)
        .addMigrations(MIGRATION_ASSETS_5_6)
        .addMigrations(MIGRATION_ASSETS_6_7)
        .addMigrations(MIGRATION_ASSETS_7_8)
        .addMigrations(MIGRATION_ASSETS_8_9)
        .addMigrations(MIGRATION_ASSETS_9_10)
        .addMigrations(MIGRATION_ASSETS_10_11)
        .addMigrations(MIGRATION_ASSETS_11_12)
        .addMigrations(MIGRATION_ASSETS_12_13)
        .addMigrations(MIGRATION_ASSETS_13_14)
        .addMigrations(MIGRATION_ASSETS_14_15)
}

private val MIGRATION_ASSETS_14_15 = object : Migration(14, 15) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_13_14 = object : Migration(13, 14) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_12_13 = object : Migration(12, 13) {
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

private val MIGRATION_ASSETS_11_12 = object : Migration(11, 12) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE srb_lat ADD COLUMN status TEXT NOT NULL DEFAULT 'UNKNOWN'")
        db.execSQL("ALTER TABLE srb_lat ADD COLUMN status_time TEXT")
    }
}

private val MIGRATION_ASSETS_10_11 = object : Migration(10, 11) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_9_10 = object : Migration(9, 10) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_8_9 = object : Migration(8, 9) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_7_8 = object : Migration(7, 8) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_5_6 = object : Migration(5, 6) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE srb_lat ADD COLUMN unused INTEGER NOT NULL DEFAULT 0")
    }
}

private val MIGRATION_ASSETS_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}





