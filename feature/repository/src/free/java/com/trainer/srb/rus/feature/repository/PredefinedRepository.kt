package com.trainer.srb.rus.feature.repository

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

    override val usedTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        predefinedRepositoryDao.getUsed().collect {
            val translations = it.map { serbianToRussianWord ->
                serbianToRussianWord.toTranslation()
            }
            emit(translations)
        }
    }

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getWord(serbianLatinId)?.toTranslation()
        }
    }

    override suspend fun markAsUnused(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            val srbLatinWord = SerbianLatinWord(
                id = translation.source.latinId,
                word = translation.source.latinValue,
                unused = true
            )
            predefinedRepositoryDao.update(srbLatinWord)
        }
    }

    override suspend fun markAsUnusedById(latinId: Long) {
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getWord(latinId)?.let {
                val unused = it.serbianLat.copy(
                    unused = true
                )
                predefinedRepositoryDao.update(unused)
            }
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

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val random = predefinedRepositoryDao.getRandom(randomTranslationsCount)
            random.map {
                it.toTranslation()
            }
        }
    }
}