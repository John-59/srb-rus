package com.trainer.srb.rus.core.dictionary

data class YandexTranslationRequestDto(
    val sourceLanguageCode: String,
    val targetLanguageCode: String,
    val texts: List<String>
)
