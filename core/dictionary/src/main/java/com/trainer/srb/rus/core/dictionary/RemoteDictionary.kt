package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDictionary @Inject constructor(
    private val yandexApi: IYandexTranslateApi,
    private val googleApi: IGoogleTranslateApi
): IRemoteDictionary {
    override suspend fun searchRusToSrb(russianWord: String): List<Translation<Word.Serbian, Word.Russian>> {
        val yandexRequest = YandexTranslationRequestDto(
            sourceLanguageCode = "ru",
            targetLanguageCode = "sr-Latn",
            texts = listOf(russianWord)
        )
        val googleRequest = GoogleTranslationRequestDto(
            q =  russianWord,
            source = "ru",
            target = "sr"
        )

        val yandexResponse = withContext(Dispatchers.IO) {
            yandexApi.translate(yandexRequest)
        }
        val googleResponse = withContext(Dispatchers.IO) {
            googleApi.translate(googleRequest)
        }

        val result = yandexResponse.translations.map {
            Translation(
                source = Word.Serbian(
                    latinValue = it.text
                ),
                translations = listOf(
                    Word.Russian(value = russianWord)
                ),
                type = TranslationSourceType.YANDEX,
                learningStatus = LearningStatus.Unknown()
            )
        }.toMutableList()
        googleResponse.data.translations.forEach {
            result.add(
                Translation(
                    source = Word.Serbian(
                        cyrillicValue = it.translatedText
                    ),
                    translations = listOf(
                        Word.Russian(value = russianWord)
                    ),
                    type = TranslationSourceType.GOOGLE,
                    learningStatus = LearningStatus.Unknown()
                )
            )
        }
        return result
    }

    override suspend fun searchSrbToRus(serbianWord: String): List<Translation<Word.Serbian, Word.Russian>> {
        val yandexRequest = YandexTranslationRequestDto(
            sourceLanguageCode = "sr",
            targetLanguageCode = "ru",
            texts = listOf(serbianWord)
        )
        val googleRequest = GoogleTranslationRequestDto(
            q = serbianWord,
            source = "sr",
            target = "ru"
        )

        val yandexResponse = withContext(Dispatchers.IO) {
            yandexApi.translate(yandexRequest)
        }
        val googleResponse = withContext(Dispatchers.IO) {
            googleApi.translate(googleRequest)
        }

        val result = listOf(
            Translation(
                source = Word.Serbian(
                    latinValue = serbianWord
                ),
                translations = yandexResponse.translations.map {
                    Word.Russian(value = it.text)
                },
                type = TranslationSourceType.YANDEX,
                learningStatus = LearningStatus.Unknown()
            ),
            Translation(
                source = Word.Serbian(
                    latinValue = serbianWord
                ),
                translations = googleResponse.data.translations.map {
                    Word.Russian(value = it.translatedText)
                },
                type = TranslationSourceType.GOOGLE,
                learningStatus = LearningStatus.Unknown()
            )
        )
        return result
    }
}