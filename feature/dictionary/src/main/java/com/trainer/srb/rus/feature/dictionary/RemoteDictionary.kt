package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import javax.inject.Inject

class RemoteDictionary @Inject constructor(): IRemoteDictionary {
    override fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }
}