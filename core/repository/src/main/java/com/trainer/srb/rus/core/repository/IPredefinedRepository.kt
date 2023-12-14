package com.trainer.srb.rus.core.repository

interface IPredefinedRepository {
    fun add(wordSerbianLatin: String, wordSerbianCyrillic: String, wordsRussian: List<String>)
}