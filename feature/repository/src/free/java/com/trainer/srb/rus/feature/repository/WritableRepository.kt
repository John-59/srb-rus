package com.trainer.srb.rus.feature.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WritableRepository @Inject constructor(
    private val innerRepositoryDao: InnerRepositoryDao
): IWritableRepository {
    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        val translationToRussian = TranslationToRussian(
            srbLatWord = translation.source.latinValue,
            srbCyrWord = translation.source.cyrillicValue,
            rusWords = translation.translations.map {
                it.value
            }
        )
        innerRepositoryDao.insert(translationToRussian)
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val found = innerRepositoryDao.searchInSrbLat(value)
            found.map {
                convertToTranslation(it)
            }
        }
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            innerRepositoryDao.getAllByAlphabet().map {
                convertToTranslation(it)
            }
        }
    }

    private fun convertToTranslation(word: SerbianToRussianWord): Translation<Word.Serbian, Word.Russian> {
        return Translation(
            id = word.serbianLat.id,
            source = Word.Serbian(
                latinValue = word.serbianLat.word,
                cyrillicValue = word.serbianCyr?.word.orEmpty()
            ),
            translations = word.russians.map {
                Word.Russian(it.word)
            }
        )
    }
}