package com.trainer.srb.rus.feature.repository

data class TranslationToRussian(
    val srbLatWord: String,
    val srbCyrWord: String,
    val rusWords: List<String>
)
