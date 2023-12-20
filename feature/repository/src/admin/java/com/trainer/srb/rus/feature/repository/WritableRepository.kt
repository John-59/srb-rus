package com.trainer.srb.rus.feature.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WritableRepository @Inject constructor(
    private val predefinedRepositoryDao: PredefinedRepositoryDao
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
            predefinedRepositoryDao.insert(translationToRussian)
            makeCheckpoint()
        }
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val found = predefinedRepositoryDao.searchInSrbLat(value)
            found.map {
                convertToTranslation(it)
            }
        }
    }

    override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getAllByAlphabet().map {
                convertToTranslation(it)
            }
        }
    }

    private suspend fun makeCheckpoint() {
        predefinedRepositoryDao.checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
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