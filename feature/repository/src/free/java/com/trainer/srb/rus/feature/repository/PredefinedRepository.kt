package com.trainer.srb.rus.feature.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
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
                serbianToRussianWord.toTranslation(TranslationSourceType.PREDEFINED)
            }
            emit(translations)
        }
    }

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getWord(serbianLatinId)?.toTranslation(TranslationSourceType.PREDEFINED)
        }
    }

    override suspend fun markAsUnused(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            markAsUnusedById(translation.source.latinId)
        }
    }

    override suspend fun markAsUnusedById(latinId: Long) {
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getWord(latinId)?.let {
                val unused = it.serbianLat.copy(
                    status = WordStatus.Unused
                )
                predefinedRepositoryDao.update(unused)
            }
        }
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val found = predefinedRepositoryDao.searchInSrbLat(value)
            found.map {
                it.toTranslation(TranslationSourceType.PREDEFINED)
            }
        }
    }

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val random = predefinedRepositoryDao.getRandom(randomTranslationsCount)
            random.map {
                it.toTranslation(TranslationSourceType.PREDEFINED)
            }
        }
    }
}