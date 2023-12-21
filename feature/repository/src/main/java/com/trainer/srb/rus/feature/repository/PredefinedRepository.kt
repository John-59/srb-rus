package com.trainer.srb.rus.feature.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PredefinedRepository @Inject constructor(
    private val predefinedRepositoryDao: PredefinedRepositoryDao
): IPredefinedRepository {

    override val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        predefinedRepositoryDao.getAll().collect {
            val translations = it.map {
                it.toTranslation()
            }
            emit(translations)
        }
    }

    override suspend fun addSrbToRusTranslation(
        srbToRusTranslation: Translation<Word.Serbian, Word.Russian>
    ) {
        withContext(Dispatchers.IO) {
            val translationToRussian = TranslationToRussian(
                srbLatWord = srbToRusTranslation.source.latinValue,
                srbCyrWord = srbToRusTranslation.source.cyrillicValue,
                rusWords = srbToRusTranslation.translations.map {
                    it.value
                }
            )
            predefinedRepositoryDao.insert(translationToRussian)
            makeCheckpoint()
        }
    }

    override suspend fun removeSrbToRusTranslation(
        srbToRusTranslation: Translation<Word.Serbian, Word.Russian>
    ) {
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.remove(srbToRusTranslation.source.latinId)
            makeCheckpoint()
        }
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val found = predefinedRepositoryDao.searchInSrbLat(value)
            found.map {
                it.toTranslation()
            }
        }
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getAllByAlphabet().map {
                it.toTranslation()
            }
        }
    }

    private suspend fun makeCheckpoint() {
        predefinedRepositoryDao.checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
    }
}