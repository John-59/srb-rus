package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

interface IRemoteDictionary {
    fun searchRusToSrb(russianWord: String): List<Translation<Word.Serbian, Word.Russian>>

    fun searchSrbToRus(serbianWord: String): List<Translation<Word.Serbian, Word.Russian>>
}