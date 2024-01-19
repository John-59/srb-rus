package com.trainer.srb.rus.feature.repository

import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun SerbianToRussianWord.toTranslation(
    translationSourceType: TranslationSourceType
): Translation<Word.Serbian, Word.Russian> {
    return Translation(
        source = Word.Serbian(
            latinId = serbianLat.id,
            cyrillicId = serbianCyr?.id ?: 0,
            latinValue = serbianLat.word,
            cyrillicValue = serbianCyr?.word.orEmpty()
        ),
        translations = russians.map {
            Word.Russian(
                id = it.id,
                value = it.word
            )
        },
        type = translationSourceType,
        learningStatus = this.serbianLat.status.toLearningStatus(
            serbianLat.statusDateTime ?: Clock.System.now().toLocalDateTime(TimeZone.UTC)
        )
    )
}

fun Translation<Word.Serbian, Word.Russian>.toSerbianToRussianWord(): SerbianToRussianWord {
    return SerbianToRussianWord(
        serbianLat = SerbianLatinWord(
            id = source.latinId,
            word = source.latinValue,
            status = this.learningStatus.toWordStatus(),
            statusDateTime = this.learningStatus.dateTime
        ),
        serbianCyr = if (source.cyrillicValue.isNotBlank()) {
            SerbianCyrillicWord(
                id = source.cyrillicId,
                word = source.cyrillicValue,
                latId = source.latinId
            )
        } else {
            null
        },
        russians = translations.map {
            RussianWord(
                id = it.id,
                word = it.value
            )
        }
    )
}

fun LearningStatus.toWordStatus(): WordStatus {
    return when (this) {
        is LearningStatus.Unknown -> WordStatus.Unknown
        is LearningStatus.Unused -> WordStatus.Unused
        is LearningStatus.New -> WordStatus.New
        is LearningStatus.NextDay -> WordStatus.NextDay
        is LearningStatus.AfterTwoDays -> WordStatus.AfterTwoDays
        is LearningStatus.AfterThreeDays -> WordStatus.AfterThreeDays
        is LearningStatus.AfterWeek -> WordStatus.AfterWeek
        is LearningStatus.AfterTwoWeeks -> WordStatus.AfterTwoWeeks
        is LearningStatus.AfterMonth -> WordStatus.AfterMonth
        is LearningStatus.AlreadyKnow -> WordStatus.AlreadyKnow
        is LearningStatus.DontWantLearn -> WordStatus.DontWantLearn
    }
}

fun WordStatus.toLearningStatus(dateTime: LocalDateTime): LearningStatus {
    return when (this) {
        WordStatus.Unknown -> LearningStatus.Unknown(dateTime)
        WordStatus.Unused -> LearningStatus.Unused(dateTime)
        WordStatus.New -> LearningStatus.New(dateTime)
        WordStatus.NextDay -> LearningStatus.NextDay(dateTime)
        WordStatus.AfterTwoDays -> LearningStatus.AfterTwoDays(dateTime)
        WordStatus.AfterThreeDays -> LearningStatus.AfterThreeDays(dateTime)
        WordStatus.AfterWeek -> LearningStatus.AfterWeek(dateTime)
        WordStatus.AfterTwoWeeks -> LearningStatus.AfterTwoWeeks(dateTime)
        WordStatus.AfterMonth -> LearningStatus.AfterMonth(dateTime)
        WordStatus.AlreadyKnow -> LearningStatus.AlreadyKnow(dateTime)
        WordStatus.DontWantLearn -> LearningStatus.DontWantLearn(dateTime)
    }
}