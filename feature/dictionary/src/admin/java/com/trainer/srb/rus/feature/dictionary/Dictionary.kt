package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.LearningStatusName
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.dictionary.canRepeat
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.flow.Flow
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

    override val translationsForRepeat: Flow<List<Translation<Word.Serbian, Word.Russian>>> = translations.map {
        val now = Clock.System.now()
        it.filter {  translation ->
            translation.canRepeat(now)
        }
    }

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

    override suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>) {
        update(translation)
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.update(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.remove(translation)
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