package com.trainer.srb.rus.mocks

import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word

val translationsExample = listOf(
    Translation(
        source = Word.Serbian(
            latinValue = "već",
            cyrillicValue = "већ"
        ),
        translations = listOf(
            Word.Russian(value = "а"),
            Word.Russian(value = "уже")
        ),
        type = TranslationSourceType.USER,
        learningStatus = LearningStatus.Unknown()
    ),
    Translation(
        source = Word.Serbian(
            latinValue = "šargarepa",
            cyrillicValue = "шаргарепа"
        ),
        translations = listOf(
            Word.Russian(value = "морковь")
        ),
        type = TranslationSourceType.USER,
        learningStatus = LearningStatus.Unknown()
    ),
    Translation(
        source = Word.Serbian(
            latinValue = "someWord",
            cyrillicValue = ""
        ),
        translations = listOf(
            Word.Russian(value = "перевод 1"),
            Word.Russian(value = "перевод 2"),
            Word.Russian(value = "перевод 3"),
            Word.Russian(value = "перевод 4"),
            Word.Russian(value = "перевод 5")
        ),
        type = TranslationSourceType.USER,
        learningStatus = LearningStatus.Unknown()
    ),
    Translation(
        source = Word.Serbian(
            latinValue = "kiša",
            cyrillicValue = ""
        ),
        translations = listOf(
            Word.Russian(value = "дождь")
        ),
        type = TranslationSourceType.USER,
        learningStatus = LearningStatus.Unknown()
    )
)