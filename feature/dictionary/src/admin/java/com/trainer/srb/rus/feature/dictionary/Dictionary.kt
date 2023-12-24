package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository
): IDictionary {

    override val translations = writableRepository.translations

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return writableRepository.search(value)
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.add(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.remove(translation)
    }

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return writableRepository.getRandom(randomTranslationsCount)
    }
}