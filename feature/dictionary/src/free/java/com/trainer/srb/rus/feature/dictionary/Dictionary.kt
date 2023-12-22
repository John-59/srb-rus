package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.flow.combine
import java.util.UUID
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository,
    private val predefinedRepository: IPredefinedRepository
): IDictionary {

    private val readonlyUuids = mutableSetOf<UUID>()

    override val translations = writableRepository.translations
        .combine(predefinedRepository.usedTranslations) { writableTranslations, predefinedTranslations ->
            readonlyUuids.clear()
            readonlyUuids.addAll(predefinedTranslations.map { it.uuid })
            val allTranslations = writableTranslations.toMutableList()
            allTranslations.addAll(predefinedTranslations)
            allTranslations
        }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        val innerSearchResult = writableRepository.search(value)
        val predefinedSearchResult = predefinedRepository.search(value).toMutableList()
        predefinedSearchResult.addAll(innerSearchResult)
        return predefinedSearchResult
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.add(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        if (readonlyUuids.contains(translation.uuid)) {
            predefinedRepository.markAsUnused(translation)
        } else {
            writableRepository.remove(translation)
        }
    }
}