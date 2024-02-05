package com.trainer.srb.rus.core.repository

import androidx.room.TypeConverter

class WordStatusConverter {

    @TypeConverter
    fun fromString(statusName: String?): WordStatus {
        return statusName?.let {
            WordStatus.create(it)
        } ?: WordStatus.Unknown
    }

    @TypeConverter
    fun fromStatus(status: WordStatus?): String {
        return status?.let {
            WordStatus.getName(it)
        }  ?: ""
    }
}