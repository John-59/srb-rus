package com.trainer.srb.rus.core.dictionary

interface IInnerDictionary {
    suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>

    suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>>
}