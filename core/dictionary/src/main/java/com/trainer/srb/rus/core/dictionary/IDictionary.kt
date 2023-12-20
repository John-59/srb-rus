package com.trainer.srb.rus.core.dictionary

interface IDictionary {
    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun add(translation: Translation<Word.Serbian, Word.Russian>)
}