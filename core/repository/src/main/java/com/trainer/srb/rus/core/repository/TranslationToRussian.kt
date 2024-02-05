package com.trainer.srb.rus.core.repository

import kotlinx.datetime.LocalDateTime

data class TranslationToRussian(
    val srbLatWord: String,
    val srbCyrWord: String,
    val rusWords: List<String>,
    val status: WordStatus,
    val statusDateTime: LocalDateTime
)
