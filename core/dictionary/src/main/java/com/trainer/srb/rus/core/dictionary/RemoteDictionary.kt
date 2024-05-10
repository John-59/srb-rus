package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import javax.inject.Inject

class RemoteDictionary @Inject constructor(): IRemoteDictionary {
    override fun searchRusToSrb(russianWord: String): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }

    override fun searchSrbToRus(serbianWord: String): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }
}