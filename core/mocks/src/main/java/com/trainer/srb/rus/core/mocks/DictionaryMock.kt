package com.trainer.srb.rus.core.mocks

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

class DictionaryMock: IDictionary {
    override val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        emit(translationsExample)
    }

    override val isNewWords: Flow<Boolean> = emptyFlow()

    override val translationsForRepeat: Flow<List<Translation<Word.Serbian, Word.Russian>>> = emptyFlow()

    override val isWordsForRepeat: Flow<Boolean> = emptyFlow()

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return null
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
    }

    override suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>) {
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
    }

    override suspend fun getRandom(
        randomTranslationsCount: Int,
        vararg statuses: LearningStatusName
    ): List<Translation<Word.Serbian, Word.Russian>> {
        return translationsExample.filter { example ->
            statuses.contains(example.learningStatus.name)
        }.shuffled().take(randomTranslationsCount)
    }
}