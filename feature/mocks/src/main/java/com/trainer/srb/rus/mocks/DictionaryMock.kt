package com.trainer.srb.rus.mocks

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryMock: IDictionary {
    override val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        val translations = listOf(
            Translation<Word.Serbian, Word.Russian>(
                source = Word.Serbian(
                    latinId = 1,
                    latinValue = "kiša",
                    cyrillicId = 1,
                    cyrillicValue = "киша"
                ),
                translations = listOf(
                    Word.Russian(
                        id = 1,
                        value = "дождь"
                    )
                )
            )
        )
        emit(translations)
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
    }

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return emptyList()
    }
}