package com.trainer.srb.rus.core.dictionary

interface IRemoteDictionary {
    fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>
}