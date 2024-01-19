package com.trainer.srb.rus.core.dictionary

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun Translation<Word.Serbian, Word.Russian>.serbianAsString(delimiter: String = " / "): String {
    return listOf(source.latinValue, source.cyrillicValue).joinToString(delimiter)
}

fun Translation<Word.Serbian, Word.Russian>.russianAsString(delimiter: String = ", "): String {
    return translations.joinToString(delimiter) {
        it.value
    }
}

fun Translation<Word.Serbian, Word.Russian>.canRepeat(now: Instant): Boolean {
    val status = this.learningStatus
    val statusMidnight = LocalDateTime(
        year = status.dateTime.year,
        month = status.dateTime.month,
        dayOfMonth = status.dateTime.dayOfMonth,
        hour = status.dateTime.hour,
        minute = status.dateTime.minute,
        second = status.dateTime.second,
        nanosecond = status.dateTime.nanosecond
    ).toInstant(TimeZone.currentSystemDefault())
    when (status) {
        is LearningStatus.AfterMonth -> {
            return (now - statusMidnight).inWholeDays >= 30
        }
        is LearningStatus.AfterThreeDays -> {
            return (now - statusMidnight).inWholeDays >= 3
        }
        is LearningStatus.AfterTwoDays -> {
            return (now - statusMidnight).inWholeDays >= 2
        }
        is LearningStatus.AfterTwoWeeks -> {
            return (now - statusMidnight).inWholeDays >= 14
        }
        is LearningStatus.AfterWeek -> {
            return (now - statusMidnight).inWholeDays >= 7
        }
        is LearningStatus.AlreadyKnow -> return false
        is LearningStatus.DontWantLearn -> return false
        is LearningStatus.New -> return false
        is LearningStatus.NextDay -> {
            return (now - statusMidnight).inWholeDays >= 1
        }
        is LearningStatus.Unknown -> return false
        is LearningStatus.Unused -> return false
    }
}

fun Translation<Word.Serbian, Word.Russian>.getProgress(): Float {
    return when (this.learningStatus) {
        is LearningStatus.AfterMonth -> 6f / 7
        is LearningStatus.AfterThreeDays -> 3f / 7
        is LearningStatus.AfterTwoDays -> 2f / 7
        is LearningStatus.AfterTwoWeeks -> 5f / 7
        is LearningStatus.AfterWeek -> 4f / 7
        is LearningStatus.AlreadyKnow -> 1f
        is LearningStatus.DontWantLearn -> 0f
        is LearningStatus.New -> 0f
        is LearningStatus.NextDay -> 1f / 7
        is LearningStatus.Unknown -> 0f
        is LearningStatus.Unused -> 0f
    }
}