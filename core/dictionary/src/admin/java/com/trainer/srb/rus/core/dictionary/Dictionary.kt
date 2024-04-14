package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.repository.IWritableRepository
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.SerbianLatinWordId
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.canRepeat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.datetime.Clock
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository
): IDictionary {

    override val translations = writableRepository.translations

    override val isNewWords: Flow<Boolean> = translations.transform {
        val containsNew = it.any {  translation ->
            translation.learningStatus is LearningStatus.New
        }
        emit(containsNew)
    }
    override val isUnknownWords = writableRepository.translations.transform {
        val containsUnknown = it.any { translation ->
            translation.learningStatus is LearningStatus.Unknown
        }
        emit(containsUnknown)
    }

    override val totalTranslationsCount: Flow<Int> = writableRepository.totalTranslationsCount

    // in admin version no users translations
    override val userTranslationCount: Flow<Int> = emptyFlow()

    override val learningTranslationsCount: Flow<Int> = writableRepository.learningTranslationsCount

    override val learnedTranslationsCount: Flow<Int> = writableRepository.learnedTranslationsCount

    override val translationsForRepeat: Flow<List<Translation<Word.Serbian, Word.Russian>>> = translations.map {
        val now = Clock.System.now()
        it.filter {  translation ->
            translation.canRepeat(now)
        }
    }
    override val translationsForRepeatAgain: Flow<List<Translation<Word.Serbian, Word.Russian>>> =
        writableRepository.repeatAgainTranslationIds.map { ids ->
            ids.mapNotNull { serbianLatinWordId ->
                when (serbianLatinWordId) {
                    is SerbianLatinWordId.Predefined -> writableRepository.get(serbianLatinWordId.id)
                    is SerbianLatinWordId.User -> null // in admin version user dictionary not exists
                }
            }
        }

    override val translationsForRepeatAgainCount: Flow<Int> = writableRepository.repeatAgainTranslationCount

    override val isWordsForRepeat: Flow<Boolean> = translationsForRepeat.map {
        it.isNotEmpty()
    }

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return writableRepository.get(serbianLatinId)
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return writableRepository.search(value)
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        // for admin version all new words checked as unknown in order to users learning
        // such words as random word from predefined dictionary.
        translation.learningStatus = LearningStatus.Unknown()
        writableRepository.add(translation)
    }

    /**
     * Add the translation to special part of the dictionary where store words for
     * the exercise "Repeat again".
     * This method do nothing, because in admin version the "Repeat again" exercise is disabled.
     */
    override suspend fun addToRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.addToRepeatAgain(translation)
    }

    override suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>) {
        update(translation)
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.update(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.remove(translation)
    }

    /**
     * Remove the translation from special part of the dictionary where store words for
     * the exercise "Repeat again".
     * This method do nothing, because in admin version the "Repeat again" exercise is disabled.
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
        return writableRepository.getRandom(randomTranslationsCount, usedStatuses)
    }
}