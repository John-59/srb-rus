package com.trainer.srb.rus.core.dictionary

data class Translation<T, U>(
    val id: Long,
    val source: T,
    val translations: List<U>
) where T: Word, U: Word
