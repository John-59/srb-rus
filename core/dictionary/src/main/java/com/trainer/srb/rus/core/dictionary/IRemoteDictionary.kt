package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

interface IRemoteDictionary {
    fun search(value: String): List<Translation<Word.Serbian, Word.Russian>>
}