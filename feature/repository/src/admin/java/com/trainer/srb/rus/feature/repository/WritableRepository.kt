package com.trainer.srb.rus.feature.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.LearningStatusName
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class WritableRepository @Inject constructor(
    private val predefinedRepositoryDao: PredefinedRepositoryDao
): IWritableRepository {

    override val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        predefinedRepositoryDao.getAll().collect {
            val translations = it.map { word ->
                word.toTranslation(TranslationSourceType.PREDEFINED)
            }
            emit(translations)
        }
    }


    // not means for admin version
    override val predefinedStatuses: Flow<List<Pair<Long, LearningStatus>>> = emptyFlow()

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return withContext(Dispatchers.IO) {
            predefinedRepositoryDao.getWord(serbianLatinId)?.toTranslation(TranslationSourceType.PREDEFINED)
        }
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            val translationToRussian = TranslationToRussian(
                srbLatWord = translation.source.latinValue,
                srbCyrWord = translation.source.cyrillicValue,
                rusWords = translation.translations.map {
                    it.value
                },
                status = translation.learningStatus.toWordStatus(),
                statusDateTime = translation.learningStatus.dateTime
            )
            predefinedRepositoryDao.insert(translationToRussian)
            makeCheckpoint()
        }
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            predefinedRepositoryDao.remove(translation.source.latinId)
            makeCheckpoint()
        }
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            val serbianToRussianWord = translation.toSerbianToRussianWord()
            predefinedRepositoryDao.update(serbianToRussianWord)
            makeCheckpoint()
        }
    }

    override suspend fun addLinkToPredefinedTranslation(translation: Translation<Word.Serbian, Word.Russian>) {
        // for admin version adding link to predefined translation has no means,
        // because admin can change predefined repo directly.
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
            val randoms = predefinedRepositoryDao.getRandom(randomTranslationsCount, statuses)
            randoms.map {
                it.toTranslation(TranslationSourceType.PREDEFINED)
            }
        }
    }

    private suspend fun makeCheckpoint() {
        predefinedRepositoryDao.checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
    }
}