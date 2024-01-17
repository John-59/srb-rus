package com.trainer.srb.rus.core.dictionary

import java.util.UUID

class Translation<T, U>(
    val source: T,
    val translations: List<U>,
    var type: TranslationSourceType,
    var learningStatus: LearningStatus,
    val uuid: UUID = UUID.randomUUID(),
) where T: Word, U: Word {

    fun contains(word: String): Boolean {
        return source.contains(word) || translations.any { it.contains(word) }
    }

    fun contentEqual(other: Translation<T, U>): Boolean {
        return source.contentEqual(other.source)
                && translations.count() == other.translations.count()
                && translations.all { translation ->
                    other.translations.singleOrNull {
                        translation.contentEqual(it)
                    } != null
                }
    }
}