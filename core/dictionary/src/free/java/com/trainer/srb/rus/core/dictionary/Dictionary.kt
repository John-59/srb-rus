package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.SerbianLatinWordId
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.canRepeat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.datetime.Clock
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository,
    private val predefinedRepository: IPredefinedRepository
): IDictionary {

    override val translations = writableRepository.translations
        .combine(predefinedRepository.usedTranslations) { writableTranslations, predefinedTranslations ->
            writableTranslations + predefinedTranslations
        }

    override val isNewWords: Flow<Boolean> = translations.transform {
        val containsNew = it.any {  translation ->
            translation.learningStatus is LearningStatus.New
        }
        emit(containsNew)
    }

    override val isUnknownWords: Flow<Boolean> = predefinedRepository.usedTranslations.transform {
        val containsUnknown = it.any { translation ->
            translation.learningStatus is LearningStatus.Unknown
        }
        emit(containsUnknown)
    }

    override val totalTranslationsCount: Flow<Int> = combine(
        writableRepository.totalTranslationsCount,
        predefinedRepository.totalTranslationsCount
    ) { userTotalTranslationsCount, predefinedTotalTranslationsCount ->
        userTotalTranslationsCount + predefinedTotalTranslationsCount
    }

    override val userTranslationCount: Flow<Int> = writableRepository.totalTranslationsCount

    override val learningTranslationsCount: Flow<Int> = combine(
        writableRepository.learningTranslationsCount,
        predefinedRepository.learningTranslationsCount
    ) { userLearningCount, predefinedLearningCount ->
        userLearningCount + predefinedLearningCount
    }

    override val learnedTranslationsCount: Flow<Int> = combine(
        writableRepository.learnedTranslationsCount,
        predefinedRepository.learnedTranslationsCount
    ) { userLearnedCount, predefinedLearnedCount ->
        userLearnedCount + predefinedLearnedCount
    }

    override val translationsForRepeat: Flow<List<Translation<Word.Serbian, Word.Russian>>> = translations.map {
        val now = Clock.System.now()
        it.filter {  translation ->
            translation.canRepeat(now)
        }
    }

    /**
     * The list of words for exercise "Repeat again".
     */
    override val translationsForRepeatAgain: Flow<List<Translation<Word.Serbian, Word.Russian>>> =
        writableRepository.repeatAgainTranslationIds.map { ids ->
            ids.mapNotNull { serbianLatinWordId ->
                when (serbianLatinWordId) {
                    is SerbianLatinWordId.Predefined -> predefinedRepository.get(serbianLatinWordId.id)
                    is SerbianLatinWordId.User -> writableRepository.get(serbianLatinWordId.id)
                }
            }
        }

    override val translationsForRepeatAgainCount: Flow<Int> = writableRepository.repeatAgainTranslationCount

    override val isWordsForRepeat: Flow<Boolean> = translationsForRepeat.map {
        it.isNotEmpty()
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

    /**
     * Add the translation to special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    override suspend fun addToRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.addToRepeatAgain(translation)
    }

    override suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>) {
        when (translation.type) {
            TranslationSourceType.PREDEFINED -> {
                val status = translation.learningStatus
                translation.learningStatus = LearningStatus.Unused()
                writableRepository.addLinkToPredefinedTranslation(translation)

                translation.type = TranslationSourceType.USER
                translation.learningStatus = status
                writableRepository.add(translation)
            }
            TranslationSourceType.USER -> {
                update(translation)
            }
            TranslationSourceType.YANDEX -> {
                // do nothing
            }

            TranslationSourceType.GOOGLE -> {
                // do nothing
            }
        }
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        when (translation.type) {
            TranslationSourceType.PREDEFINED -> {
                predefinedRepository.update(translation)
                writableRepository.addLinkToPredefinedTranslation(translation)
            }
            TranslationSourceType.USER -> {
                writableRepository.update(translation)
            }
            TranslationSourceType.YANDEX -> {
                // do nothing
            }

            TranslationSourceType.GOOGLE -> {
                // do nothing
            }
        }
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        when (translation.type) {
            TranslationSourceType.PREDEFINED -> {
                translation.learningStatus = LearningStatus.Unused()
                predefinedRepository.update(translation)
                writableRepository.addLinkToPredefinedTranslation(translation)
            }
            TranslationSourceType.USER -> {
                writableRepository.remove(translation)
            }
            TranslationSourceType.YANDEX -> {
                // do nothing
            }

            TranslationSourceType.GOOGLE -> {
                // do nothing
            }
        }
    }

    /**
     * Remove the translation from special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    override suspend fun removeFromRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.removeFromRepeatAgain(translation)
    }

    override suspend fun getRandom(
        randomTranslationsCount: Int,
        vararg statuses: LearningStatusName
    ): List<Translation<Word.Serbian, Word.Russian>> {
        val usedStatuses = if (statuses.isEmpty()) {
            LearningStatusName.entries.toList()
        } else {
            statuses.toList()
        }
        return when {
            randomTranslationsCount <= 0 -> {
                emptyList()
            }

            randomTranslationsCount == 1 -> {
                val random = listOfNotNull(
                    predefinedRepository.getRandom(1, usedStatuses).firstOrNull(),
                    writableRepository.getRandom(1, usedStatuses).firstOrNull()
                ).randomOrNull()
                listOfNotNull(random)
            }

            else -> {
                val fromPredefined = predefinedRepository.getRandom(randomTranslationsCount, usedStatuses)
                val fromWritable = writableRepository.getRandom(randomTranslationsCount, usedStatuses)
                (fromPredefined + fromWritable).shuffled().take(
                    randomTranslationsCount
                )
            }
        }
    }
}