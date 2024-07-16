package com.trainer.srb.rus.core.repository

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
        .addMigrations(MIGRATION_ASSETS_15_16)
        .addMigrations(MIGRATION_ASSETS_16_17)
        .addMigrations(MIGRATION_ASSETS_17_18)
        .addMigrations(MIGRATION_ASSETS_18_19)
        .addMigrations(MIGRATION_ASSETS_19_20)
        .addMigrations(MIGRATION_ASSETS_20_21)
        .addMigrations(MIGRATION_ASSETS_21_22)
        .addMigrations(MIGRATION_ASSETS_22_23)
        .addMigrations(MIGRATION_ASSETS_23_24)
        .addMigrations(MIGRATION_ASSETS_24_25)
        .addMigrations(MIGRATION_ASSETS_25_26)
        .addMigrations(MIGRATION_ASSETS_26_27)
        .addMigrations(MIGRATION_ASSETS_27_28)
        .addMigrations(MIGRATION_ASSETS_28_29)
        .addMigrations(MIGRATION_ASSETS_29_30)
}

private val MIGRATION_ASSETS_29_30 = object : Migration(29, 30) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_28_29 = object : Migration(28, 29) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_27_28 = object : Migration(27, 28) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_26_27 = object : Migration(26, 27) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_25_26 = object : Migration(25, 26) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_24_25 = object : Migration(24, 25) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_23_24 = object : Migration(23, 24) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_22_23 = object : Migration(22, 23) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_21_22 = object : Migration(21, 22) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_20_21 = object : Migration(20, 21) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_19_20 = object : Migration(19, 20) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_18_19 = object : Migration(18, 19) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_17_18 = object : Migration(17, 18) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_16_17 = object : Migration(16, 17) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
}

private val MIGRATION_ASSETS_15_16 = object : Migration(15, 16) {
    override fun migrate(db: SupportSQLiteDatabase) {
    }
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





