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

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return writableRepository.get(serbianLatinId) ?: predefinedRepository.get(serbianLatinId)
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
            writableRepository.markAsUnused(translation)
        } else {
            writableRepository.remove(translation)
        }
    }

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return when {
            randomTranslationsCount <= 0 -> {
                emptyList()
            }

            randomTranslationsCount == 1 -> {
                val random = listOfNotNull(
                    predefinedRepository.getRandom(1).firstOrNull(),
                    writableRepository.getRandom(1).firstOrNull()
                ).randomOrNull()
                listOfNotNull(random)
            }

            else -> {
                val fromPredefined = predefinedRepository.getRandom(randomTranslationsCount)
                val fromWritable = writableRepository.getRandom(randomTranslationsCount)
                (0 until randomTranslationsCount).mapNotNull {
                    when (it % 2) {
                        0 -> fromPredefined.getOrNull(it) ?: fromWritable.getOrNull(it)
                        else -> fromWritable.getOrNull(it) ?: fromPredefined.getOrNull(it)
                    }
                }
            }
        }
    }
}