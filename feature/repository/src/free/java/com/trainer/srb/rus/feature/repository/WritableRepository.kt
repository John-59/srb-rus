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
        withContext(Dispatchers.IO) {
            val translationToRussian = TranslationToRussian(
                srbLatWord = translation.source.latinValue,
                srbCyrWord = translation.source.cyrillicValue,
                rusWords = translation.translations.map {
                    it.value
                }
            )
            innerRepositoryDao.insert(translationToRussian)
        }
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            innerRepositoryDao.remove(translation.source.latinId)
        }
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val found = innerRepositoryDao.searchInSrbLat(value)
            found.map {
                it.toTranslation()
            }
        }
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            innerRepositoryDao.getAllByAlphabet().map {
                it.toTranslation()
            }
        }
    }
}