package com.trainer.srb.rus.feature.repository

import kotlinx.datetime.LocalDateTime

data class TranslationToRussian(
    val srbLatWord: String,
    val srbCyrWord: String,
    val rusWords: List<String>,
    val status: WordStatus,
    val statusDateTime: LocalDateTime
)
