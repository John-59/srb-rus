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

fun Translation<Word.Serbian, Word.Russian>.toSerbianToRussianWord(unused: Boolean): SerbianToRussianWord {
    return SerbianToRussianWord(
        serbianLat = SerbianLatinWord(
            id = source.latinId,
            word = source.latinValue,
            unused = unused,
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
        LearningStatus.UNKNOWN -> WordStatus.UNKNOWN
        LearningStatus.UNUSED -> WordStatus.UNUSED
        LearningStatus.NEW -> WordStatus.NEW
        LearningStatus.FIRST_ACQUAINTANCE -> WordStatus.FIRST_ACQUAINTANCE
        LearningStatus.NEXT_DAY -> WordStatus.NEXT_DAY
        LearningStatus.AFTER_TWO_DAYS -> WordStatus.AFTER_TWO_DAYS
        LearningStatus.AFTER_THREE_DAYS -> WordStatus.AFTER_THREE_DAYS
        LearningStatus.AFTER_WEEK -> WordStatus.AFTER_WEEK
        LearningStatus.AFTER_TWO_WEEKS -> WordStatus.AFTER_TWO_WEEKS
        LearningStatus.AFTER_MONTH -> WordStatus.AFTER_MONTH
    }
}

fun WordStatus.toLearningStatus(): LearningStatus {
    return when (this) {
        WordStatus.UNKNOWN -> LearningStatus.UNKNOWN
        WordStatus.UNUSED -> LearningStatus.UNUSED
        WordStatus.NEW -> LearningStatus.NEW
        WordStatus.FIRST_ACQUAINTANCE -> LearningStatus.FIRST_ACQUAINTANCE
        WordStatus.NEXT_DAY -> LearningStatus.NEXT_DAY
        WordStatus.AFTER_TWO_DAYS -> LearningStatus.AFTER_TWO_DAYS
        WordStatus.AFTER_THREE_DAYS -> LearningStatus.AFTER_THREE_DAYS
        WordStatus.AFTER_WEEK -> LearningStatus.AFTER_WEEK
        WordStatus.AFTER_TWO_WEEKS -> LearningStatus.AFTER_TWO_WEEKS
        WordStatus.AFTER_MONTH -> LearningStatus.AFTER_MONTH
    }
}