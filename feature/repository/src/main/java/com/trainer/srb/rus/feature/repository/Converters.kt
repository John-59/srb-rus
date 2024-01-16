package com.trainer.srb.rus.feature.repository

import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word

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
        learningStatus = this.serbianLat.status.toLearningStatus()
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
        LearningStatus.UNKNOWN -> WordStatus.Unknown
        LearningStatus.UNUSED -> WordStatus.Unused
        LearningStatus.NEW -> WordStatus.New
        LearningStatus.FIRST_ACQUAINTANCE -> WordStatus.FirstAcquaintance
        LearningStatus.NEXT_DAY -> WordStatus.NextDay
        LearningStatus.AFTER_TWO_DAYS -> WordStatus.AfterTwoDays
        LearningStatus.AFTER_THREE_DAYS -> WordStatus.AfterThreeDays
        LearningStatus.AFTER_WEEK -> WordStatus.AfterWeek
        LearningStatus.AFTER_TWO_WEEKS -> WordStatus.AfterTwoWeeks
        LearningStatus.AFTER_MONTH -> WordStatus.AfterMonth
        LearningStatus.ALREADY_KNOW -> WordStatus.AlreadyKnow
        LearningStatus.DONT_WANT_LEARN -> WordStatus.DontWantLearn
    }
}

fun WordStatus.toLearningStatus(): LearningStatus {
    return when (this) {
        WordStatus.Unknown -> LearningStatus.UNKNOWN
        WordStatus.Unused -> LearningStatus.UNUSED
        WordStatus.New -> LearningStatus.NEW
        WordStatus.FirstAcquaintance -> LearningStatus.FIRST_ACQUAINTANCE
        WordStatus.NextDay -> LearningStatus.NEXT_DAY
        WordStatus.AfterTwoDays -> LearningStatus.AFTER_TWO_DAYS
        WordStatus.AfterThreeDays -> LearningStatus.AFTER_THREE_DAYS
        WordStatus.AfterWeek -> LearningStatus.AFTER_WEEK
        WordStatus.AfterTwoWeeks -> LearningStatus.AFTER_TWO_WEEKS
        WordStatus.AfterMonth -> LearningStatus.AFTER_MONTH
        WordStatus.AlreadyKnow -> LearningStatus.ALREADY_KNOW
        WordStatus.DontWantLearn -> LearningStatus.DONT_WANT_LEARN
    }
}