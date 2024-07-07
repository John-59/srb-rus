package com.trainer.srb.rus.core.dictionary

data class GoogleTranslationRequestDto(
    val q: String,
    val source: String,
    val target: String
)
