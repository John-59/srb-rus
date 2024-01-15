package com.trainer.srb.rus.core.dictionary

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

enum class LearningStatus(
    val dateTime: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
) {
    UNKNOWN,
    UNUSED,
    NEW,
    FIRST_ACQUAINTANCE,
    NEXT_DAY,
    AFTER_TWO_DAYS,
    AFTER_THREE_DAYS,
    AFTER_WEEK,
    AFTER_TWO_WEEKS,
    AFTER_MONTH
}