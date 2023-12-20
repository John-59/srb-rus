package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository,
    private val predefinedRepository: IPredefinedRepository
): IDictionary {
    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        val innerSearchResult = writableRepository.search(value)
        val predefinedSearchResult = predefinedRepository.search(value).toMutableList()
        predefinedSearchResult.addAll(innerSearchResult)
        return predefinedSearchResult
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        val innerDictionary = writableRepository.getAllByAlphabet().toMutableList()
        val predefinedDictionary = predefinedRepository.getAllByAlphabet()
        innerDictionary.addAll(predefinedDictionary)
        return innerDictionary.sortedBy {
            it.source.latinValue
        }
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.add(translation)
    }
}