package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.SerbianLatinWordId
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class WritableRepository @Inject constructor(
    private val innerRepositoryDao: InnerRepositoryDao
): IWritableRepository {

    override val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        innerRepositoryDao.getAll().collect {
            val translations = it.map { serbianRussianWord ->
                serbianRussianWord.toTranslation(TranslationSourceType.USER)
            }
            emit(translations)
        }
    }

    /**
     * Words that user selected for "Repeat again" exercise.
     */
    override val repeatAgainTranslationIds: Flow<List<SerbianLatinWordId>> = innerRepositoryDao
        .getRepeatAgain().map {
            it.map { repeatAgain ->
                if (repeatAgain.isPredefined) {
                    SerbianLatinWordId.Predefined(repeatAgain.latId)
                } else {
                    SerbianLatinWordId.User(repeatAgain.latId)
                }
            }
        }

    /**
     * The quantity of the words that the user selected for exercise "Repeat again".
     */
    override val repeatAgainTranslationCount: Flow<Int> = innerRepositoryDao.getRepeatAgainCount()

    override val predefinedStatuses: Flow<List<Pair<Long, LearningStatus>>> = innerRepositoryDao
        .getPredefinedStatuses().map { statuses ->
            statuses.map {
                it.predefinedLatinId to it.status.toLearningStatus(
                    it.statusDateTime ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                )
            }
        }

    override val totalTranslationsCount: Flow<Int> = innerRepositoryDao.getTotalTranslationsCount()

    override val learningTranslationsCount: Flow<Int> = innerRepositoryDao.getLearningTranslationsCount()

    override val learnedTranslationsCount: Flow<Int> = innerRepositoryDao.getLearnedTranslationsCount()

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return withContext(Dispatchers.IO) {
            innerRepositoryDao.getWord(serbianLatinId)?.toTranslation(TranslationSourceType.USER)
        }
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        if (translation.type != TranslationSourceType.USER) {
            return
        }
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
            innerRepositoryDao.insert(translationToRussian)
        }
    }

    /**
     * Add the translation to special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    override suspend fun addToRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            innerRepositoryDao.addRepeatAgain(
                RepeatAgain(
                    latId = translation.source.latinId,
                    isPredefined = (translation.type == TranslationSourceType.PREDEFINED)
                )
            )
        }
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        if (translation.type != TranslationSourceType.USER) {
            return
        }
        withContext(Dispatchers.IO) {
            innerRepositoryDao.remove(translation.source.latinId)
        }
    }

    /**
     * Remove the translation from special part of the dictionary where store words for
     * the exercise "Repeat again".
     */
    override suspend fun removeFromRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            innerRepositoryDao.removeRepeatAgain(
                RepeatAgain(
                    latId = translation.source.latinId,
                    isPredefined = (translation.type == TranslationSourceType.PREDEFINED)
                )
            )
        }
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        if (translation.type != TranslationSourceType.USER) {
            return
        }
        withContext(Dispatchers.IO) {
            val serbianToRussianWord = translation.toSerbianToRussianWord()
            innerRepositoryDao.update(serbianToRussianWord)
        }
    }

    override suspend fun addLinkToPredefinedTranslation(translation: Translation<Word.Serbian, Word.Russian>) {
        if (translation.type != TranslationSourceType.PREDEFINED) {
            return
        }
        withContext(Dispatchers.IO) {
            innerRepositoryDao.addPredefinedStatus(
                PredefinedStatus(
                    predefinedLatinId =  translation.source.latinId,
                    status = translation.learningStatus.toWordStatus(),
                    statusDateTime = translation.learningStatus.dateTime
                )
            )
        }
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val found = innerRepositoryDao.searchInSrbLat(value)
            found.map {
                it.toTranslation(TranslationSourceType.USER)
            }
        }
    }

    override suspend fun getRandom(
        randomTranslationsCount: Int,
        statuses: List<LearningStatusName>
    ): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val random = innerRepositoryDao.getRandom(randomTranslationsCount, statuses)
            random.map {
                it.toTranslation(TranslationSourceType.USER)
            }
        }
    }
}