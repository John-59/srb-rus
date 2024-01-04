package com.trainer.srb.rus.feature.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
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
    override val unusedLinks: Flow<List<Long>> = innerRepositoryDao.getUnusedLinks().map {
        it.map { unused ->
            unused.predefinedLatinId
        }
    }

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return withContext(Dispatchers.IO) {
            innerRepositoryDao.getWord(serbianLatinId)?.toTranslation(TranslationSourceType.USER)
        }
    }

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

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            val serbianToRussianWord = translation.toSerbianToRussianWord(false)
            innerRepositoryDao.update(serbianToRussianWord)
        }
    }

    override suspend fun markAsUnused(translation: Translation<Word.Serbian, Word.Russian>) {
        withContext(Dispatchers.IO) {
            innerRepositoryDao.addLinkToUnused(
                UnusedPredefined(
                    predefinedLatinId =  translation.source.latinId
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

    override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
        return withContext(Dispatchers.IO) {
            val random = innerRepositoryDao.getRandom(randomTranslationsCount)
            random.map {
                it.toTranslation(TranslationSourceType.USER)
            }
        }
    }
}