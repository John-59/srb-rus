package com.trainer.srb.rus.feature.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PredefinedRepository @Inject constructor(
    private val predefinedRepositoryDao: PredefinedRepositoryDao
): IPredefinedRepository {

    override val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        predefinedRepositoryDao.getAll().collect {
            val translations = it.map {
                convertToTranslation(it)
            }
            emit(translations)
        }
    }

    override fun addSrbToRusTranslation(
        srbToRusTranslation: Translation<Word.Serbian, Word.Russian>
    ) {
        val translationToRussian = TranslationToRussian(
            srbLatWord = srbToRusTranslation.source.value,
            srbCyrWord = srbToRusTranslation.source.cyrillicValue,
            rusWords = srbToRusTranslation.translations.map {
                it.value
            }
        )
        predefinedRepositoryDao.insert(translationToRussian)
        makeCheckpoint()
    }

    override fun removeSrbToRusTranslation(
        srbToRusTranslation: Translation<Word.Serbian, Word.Russian>
    ) {
        predefinedRepositoryDao.remove(srbToRusTranslation.id)
        makeCheckpoint()
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

    private fun makeCheckpoint() {
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