package com.trainer.srb.rus.core.dictionary

data class GoogleTranslationResponseDto(
    val data: GoogleTranslationDataDto
)

data class GoogleTranslationDataDto(
    val translations: List<GoogleTranslationDto>
)

data class GoogleTranslationDto(
    val translatedText: String
)