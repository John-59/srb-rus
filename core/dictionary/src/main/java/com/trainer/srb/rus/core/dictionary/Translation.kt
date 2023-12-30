package com.trainer.srb.rus.core.dictionary

import java.util.UUID

data class Translation<T, U>(
    val source: T,
    val translations: List<U>,
    val uuid: UUID = UUID.randomUUID(),
) where T: Word, U: Word {

    fun contains(word: String): Boolean {
        return source.contains(word) || translations.any { it.contains(word) }
    }
}
