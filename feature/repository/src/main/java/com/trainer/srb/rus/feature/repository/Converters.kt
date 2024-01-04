package com.trainer.srb.rus.feature.repository

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
        type = translationSourceType
    )
}

fun Translation<Word.Serbian, Word.Russian>.toSerbianToRussianWord(unused: Boolean): SerbianToRussianWord {
    return SerbianToRussianWord(
        serbianLat = SerbianLatinWord(
            id = source.latinId,
            word = source.latinValue,
            unused = unused
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