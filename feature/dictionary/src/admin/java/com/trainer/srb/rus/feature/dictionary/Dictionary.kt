package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository
): IDictionary {

    override val translations = writableRepository.translations

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return writableRepository.get(serbianLatinId)
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return writableRepository.search(value)
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        // for admin version all new words checked as unknown in order to users learning
        // such words as random word from predefined dictionary.
        translation.learningStatus = LearningStatus.UNKNOWN
        writableRepository.add(translation)
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.update(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.remove(translation)
    }

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return writableRepository.getRandom(randomTranslationsCount)
    }
}