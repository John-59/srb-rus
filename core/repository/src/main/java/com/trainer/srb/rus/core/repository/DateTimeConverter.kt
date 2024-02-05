package com.trainer.srb.rus.core.repository

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import java.lang.Exception

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let {
            try {
                LocalDateTime.parse(it)
            } catch (ex: Exception) {
                null
            }
        }
    }

    @TypeConverter
    fun fromDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }
}