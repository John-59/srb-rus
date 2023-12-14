package com.trainer.srb.rus.core.repository

import javax.inject.Inject

class PredefinedRepository @Inject constructor(
    private val predefinedRepositoryDao: PredefinedRepositoryDao
): IPredefinedRepository {
    override fun add(wordSerbianLatin: String, wordSerbianCyrillic: String, wordsRussian: List<String>) {
        val translationToRussian = TranslationToRussian(
            srbLatWord = wordSerbianLatin,
            srbCyrWord = wordSerbianCyrillic,
            rusWords = wordsRussian
        )
        predefinedRepositoryDao.insert(translationToRussian)
    }
}