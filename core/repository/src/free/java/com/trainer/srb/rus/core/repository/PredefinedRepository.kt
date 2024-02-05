package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
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

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        if (translation.type != TranslationSourceType.PREDEFINED) {
            return
        }
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.update(translation.toSerbianToRussianWord())
        }
    }

    override suspend fun setStatusById(latinId: Long, status: LearningStatus) {
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getWord(latinId)?.let {
                val srbLatinWord = it.serbianLat.copy(
                    status = status.toWordStatus(),
                    statusDateTime = status.dateTime
                )
                predefinedRepositoryDao.update(srbLatinWord)
            }
        }
    }

    override suspend fun resetStatuses() {
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.resetStatuses()
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

    override suspend fun getRandom(
        randomTranslationsCount: Int,
        statuses: List<LearningStatusName>
    ): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val random = predefinedRepositoryDao.getRandom(randomTranslationsCount, statuses)
            random.map {
                it.toTranslation(TranslationSourceType.PREDEFINED)
            }
        }
    }
}