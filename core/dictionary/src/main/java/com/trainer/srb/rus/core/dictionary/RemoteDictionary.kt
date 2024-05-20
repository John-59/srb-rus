package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDictionary @Inject constructor(
    private val yandexApi: IYandexTranslateApi,
    private val googleApi: IGoogleTranslateApi
): IRemoteDictionary {
    override suspend fun searchRusToSrb(
        russianWord: String,
        translator: RemoteTranslatorType
    ): Result<List<Translation<Word.Serbian, Word.Russian>>> {

        return when (translator) {
            RemoteTranslatorType.YANDEX -> searchRusToSrbByYandex(russianWord)
            RemoteTranslatorType.GOOGLE -> searchRusToSrbByGoogle(russianWord)
        }
    }

    private suspend fun searchRusToSrbByGoogle(
        russianWord: String
    ): Result<List<Translation<Word.Serbian, Word.Russian>>> {
        val googleRequest = GoogleTranslationRequestDto(
            q =  russianWord,
            source = "ru",
            target = "sr"
        )

        return try {
            val googleResponse = withContext(Dispatchers.IO) {
                googleApi.translate(googleRequest)
            }

            val result = googleResponse.data.translations.map {
                Translation(
                    source = Word.Serbian(
                        cyrillicValue = it.translatedText
                    ),
                    translations = listOf(
                        Word.Russian(value = russianWord)
                    ),
                    type = TranslationSourceType.INTERNET,
                    learningStatus = LearningStatus.Unknown()
                )
            }
            Result.success(result)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private suspend fun searchRusToSrbByYandex(
        russianWord: String
    ): Result<List<Translation<Word.Serbian, Word.Russian>>> {
        val yandexRequest = YandexTranslationRequestDto(
            sourceLanguageCode = "ru",
            targetLanguageCode = "sr-Latn",
            texts = listOf(russianWord)
        )

        return try {
            val yandexResponse = withContext(Dispatchers.IO) {
                yandexApi.translate(yandexRequest)
            }
            val result = yandexResponse.translations.map {
                Translation(
                    source = Word.Serbian(
                        latinValue = it.text
                    ),
                    translations = listOf(
                        Word.Russian(value = russianWord)
                    ),
                    type = TranslationSourceType.INTERNET,
                    learningStatus = LearningStatus.Unknown()
                )
            }
            Result.success(result)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun searchSrbToRus(
        serbianWord: String,
        translator: RemoteTranslatorType
    ): Result<List<Translation<Word.Serbian, Word.Russian>>> {

        return when (translator) {
            RemoteTranslatorType.YANDEX -> searchSrbToRusByYandex(serbianWord)
            RemoteTranslatorType.GOOGLE -> searchSrbToRusByGoogle(serbianWord)
        }
    }

    private suspend fun searchSrbToRusByGoogle(
        serbianWord: String
    ): Result<List<Translation<Word.Serbian, Word.Russian>>> {
        val googleRequest = GoogleTranslationRequestDto(
            q = serbianWord,
            source = "sr",
            target = "ru"
        )

        return try {
            val googleResponse = withContext(Dispatchers.IO) {
                googleApi.translate(googleRequest)
            }

            val result = listOf(
                Translation(
                    source = Word.Serbian(
                        latinValue = serbianWord
                    ),
                    translations = googleResponse.data.translations.map {
                        Word.Russian(value = it.translatedText)
                    },
                    type = TranslationSourceType.INTERNET,
                    learningStatus = LearningStatus.Unknown()
                )
            )
            Result.success(result)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private suspend fun searchSrbToRusByYandex(
        serbianWord: String
    ): Result<List<Translation<Word.Serbian, Word.Russian>>> {
        val yandexRequest = YandexTranslationRequestDto(
            sourceLanguageCode = "sr",
            targetLanguageCode = "ru",
            texts = listOf(serbianWord)
        )

        return try {
            val yandexResponse = withContext(Dispatchers.IO) {
                yandexApi.translate(yandexRequest)
            }

            val result = listOf(
                Translation(
                    source = Word.Serbian(
                        latinValue = serbianWord
                    ),
                    translations = yandexResponse.translations.map {
                        Word.Russian(value = it.text)
                    },
                    type = TranslationSourceType.INTERNET,
                    learningStatus = LearningStatus.Unknown()
                )
            )
            Result.success(result)
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}