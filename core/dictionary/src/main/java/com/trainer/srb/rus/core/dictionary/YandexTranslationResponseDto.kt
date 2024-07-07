package com.trainer.srb.rus.core.dictionary

data class YandexTranslationResponseDto(
    val translations: List<YandexTranslationDto>
)

data class YandexTranslationDto(
    val text: String
)