package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import javax.inject.Inject

class RemoteDictionary @Inject constructor(): IRemoteDictionary {
    override fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }
}