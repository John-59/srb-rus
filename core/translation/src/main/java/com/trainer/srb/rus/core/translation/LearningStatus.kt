package com.trainer.srb.rus.core.translation

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

sealed class LearningStatus(
    val dateTime: LocalDateTime,
    val name: LearningStatusName
) {
    class Unknown(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.UNKNOWN)

    class Unused(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.UNUSED)

    class New(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.NEW)

    class NextDay(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.NEXT_DAY)

    class AfterTwoDays(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.AFTER_TWO_DAYS)

    class AfterThreeDays(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.AFTER_THREE_DAYS)

    class AfterWeek(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.AFTER_WEEK)

    class AfterTwoWeeks(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.AFTER_TWO_WEEKS)

    class AfterMonth(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.AFTER_MONTH)

    class AlreadyKnow(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.ALREADY_KNOW)

    class DontWantLearn(
        dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    ): LearningStatus(dateTime, LearningStatusName.DONT_WANT_LEARN)
}