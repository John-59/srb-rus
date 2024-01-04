package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository,
    private val predefinedRepository: IPredefinedRepository
): IDictionary {

    override val translations = writableRepository.translations
        .combine(predefinedRepository.usedTranslations) { writableTranslations, predefinedTranslations ->
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

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        when (translation.type) {
            TranslationSourceType.PREDEFINED -> {
                predefinedRepository.markAsUnused(translation)
                writableRepository.add(translation)
            }
            TranslationSourceType.USER -> {
                writableRepository.update(translation)
            }
            TranslationSourceType.INTERNET -> {
                // not implemented yet
            }
        }
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        when (translation.type) {
            TranslationSourceType.PREDEFINED -> {
                predefinedRepository.markAsUnused(translation)
                writableRepository.markAsUnused(translation)
            }
            TranslationSourceType.USER -> {
                writableRepository.remove(translation)
            }
            TranslationSourceType.INTERNET -> {
                // not implemented yet
            }
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