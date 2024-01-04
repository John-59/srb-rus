package com.trainer.srb.rus.core.dictionary

import java.util.UUID

class Translation<T, U>(
    val source: T,
    val translations: List<U>,
    val type: TranslationSourceType,
    val uuid: UUID = UUID.randomUUID(),
) where T: Word, U: Word {

    fun contains(word: String): Boolean {
        return source.contains(word) || translations.any { it.contains(word) }
    }
}