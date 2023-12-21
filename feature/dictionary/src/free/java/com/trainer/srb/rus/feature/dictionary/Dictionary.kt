package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import java.util.UUID
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository,
    private val predefinedRepository: IPredefinedRepository
): IDictionary {

    private val writableUuids = mutableListOf<UUID>()

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        val innerSearchResult = writableRepository.search(value)
        val predefinedSearchResult = predefinedRepository.search(value).toMutableList()
        predefinedSearchResult.addAll(innerSearchResult)
        return predefinedSearchResult
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        val innerDictionary = writableRepository.getAllByAlphabet().toMutableList()
        writableUuids.addAll(
            innerDictionary.map {
                it.uuid
            }
        )
        val predefinedDictionary = predefinedRepository.getAllByAlphabet()
        innerDictionary.addAll(predefinedDictionary)
        return innerDictionary.sortedBy {
            it.source.latinValue
        }
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.add(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        if (writableUuids.contains(translation.uuid)) {
            writableRepository.remove(translation)
            writableUuids.remove(translation.uuid)
        } else {
            predefinedRepository.markAsUnused(translation)
        }
    }
}