package com.trainer.srb.rus.core.repository

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PredefinedRepository @Inject constructor(
    private val predefinedRepositoryDao: PredefinedRepositoryDao
): IPredefinedRepository {
    override val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
        predefinedRepositoryDao.get().collect {
            val translations = it.map {
                Translation(
                    id = it.serbianLat.id,
                    source = Word.Serbian(
                        latinValue = it.serbianLat.word,
                        cyrillicValue = it.serbianCyr?.word.orEmpty()
                    ),
                    translations = it.russians.map {
                        Word.Russian(it.word)
                    }
                )
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
    }

    override fun removeSrbToRusTranslation(
        srbToRusTranslation: Translation<Word.Serbian, Word.Russian>
    ) {
        predefinedRepositoryDao.remove(srbToRusTranslation.id)
    }
}