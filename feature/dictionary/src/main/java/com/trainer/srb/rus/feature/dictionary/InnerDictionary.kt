package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IInnerDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import javax.inject.Inject

class InnerDictionary @Inject constructor(
    private val predefinedRepository: IPredefinedRepository
): IInnerDictionary {
    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return predefinedRepository.search(value)
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        return predefinedRepository.getAllByAlphabet()
    }
}