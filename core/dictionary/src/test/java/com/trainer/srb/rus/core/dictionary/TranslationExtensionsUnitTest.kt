package com.trainer.srb.rus.core.dictionary

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Month

class TranslationExtensionsUnitTest {
    @Test
    fun `for next_day status canRepeat return true when day changed`() {
        val statusDateTime = LocalDateTime(
            year = 2024,
            month = Month.JANUARY,
            dayOfMonth = 19,
            hour = 23,
            minute = 34,
            second = 7,
            nanosecond = 5
        )
        val currentDateTime = LocalDateTime(
            year = 2024,
            month = Month.JANUARY,
            dayOfMonth = 20,
            hour = 0,
            minute = 3,
            second = 17,
            nanosecond = 55
        ).toInstant(TimeZone.currentSystemDefault())
        val translation = Translation<Word.Serbian, Word.Russian>(
            source = Word.Serbian(),
            translations = emptyList(),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.NextDay(statusDateTime)
        )
        val canRepeat = translation.canRepeat(currentDateTime)

        assertTrue(canRepeat)
    }
}