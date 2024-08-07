package com.trainer.srb.rus.core.mocks

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word

val translationsExample = listOf(
    Translation(
        source = Word.Serbian(
            latinValue = "već",
            cyrillicValue = "већ",
            latinId = 1
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
            cyrillicValue = "шаргарепа",
            latinId = 2
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
            cyrillicValue = "",
            latinId = 3
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
            cyrillicValue = "",
            latinId = 4
        ),
        translations = listOf(
            Word.Russian(value = "дождь")
        ),
        type = TranslationSourceType.USER,
        learningStatus = LearningStatus.Unknown()
    )
)