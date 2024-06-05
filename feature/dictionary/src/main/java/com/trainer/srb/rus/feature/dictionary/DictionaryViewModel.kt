package com.trainer.srb.rus.feature.dictionary

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import com.trainer.srb.rus.core.dictionary.RemoteTranslatorType
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.utils.language.Language
import com.trainer.srb.rus.core.utils.language.LanguageDetector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val innerDictionary: IDictionary,
    private val remoteDictionary: IRemoteDictionary
): ViewModel() {

    private enum class InternetSearchDirection {
        UNDEFINE,
        SRB_TO_RUS,
        RUS_TO_SRB
    }

    private var internetSearchDirection: InternetSearchDirection = InternetSearchDirection.UNDEFINE

    var searchingWord = MutableStateFlow(TextFieldValue(""))
        private set

    val visibleWords = innerDictionary.translations.combine(searchingWord) { words, word ->
        if (word.text.isBlank()) {
            words
        } else {
            words.filter {
                it.contains(word.text)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed()
    )

    private var _yandexSearchState: MutableStateFlow<InternetSearchState> = MutableStateFlow(InternetSearchState.Disabled)
    val yandexSearchState = _yandexSearchState.stateIn(
        scope = viewModelScope,
        initialValue = InternetSearchState.Disabled,
        started = SharingStarted.WhileSubscribed()
    )

    private var _googleSearchState: MutableStateFlow<InternetSearchState> = MutableStateFlow(InternetSearchState.Disabled)
    val googleSearchState = _googleSearchState.stateIn(
        scope = viewModelScope,
        initialValue = InternetSearchState.Disabled,
        started = SharingStarted.WhileSubscribed()
    )

    fun searchingWordChange(value: TextFieldValue) {
        searchingWord.value = value
        internetSearchDirection = InternetSearchDirection.UNDEFINE
        _yandexSearchState.value = InternetSearchState.Disabled
        _googleSearchState.value = InternetSearchState.Disabled
    }

    fun removeTranslation(translation: Translation<Word.Serbian, Word.Russian>) {
        viewModelScope.launch {
            innerDictionary.remove(translation)
        }
    }

    fun addToLearn(translation: Translation<Word.Serbian, Word.Russian>) {
        translation.learningStatus = LearningStatus.New()
        viewModelScope.launch {
            innerDictionary.update(translation)
        }
    }

    fun setSelectionToEnd() {
        searchingWord.value = searchingWord.value.copy(
            selection = TextRange(searchingWord.value.text.length)
        )
    }

    fun resetSearch() {
        searchingWord.value = searchingWord.value.copy(
            text = ""
        )
        internetSearchDirection = InternetSearchDirection.UNDEFINE
        _yandexSearchState.value = InternetSearchState.Disabled
        _googleSearchState.value = InternetSearchState.Disabled
    }

    fun internetSearchRusToSrb(russianWord: String) {
        internetSearchDirection = InternetSearchDirection.RUS_TO_SRB
        _yandexSearchState.value = InternetSearchState.Loading
        _googleSearchState.value = InternetSearchState.Loading
        viewModelScope.launch {
            remoteDictionary.searchRusToSrb(russianWord, RemoteTranslatorType.YANDEX)
                .onFailure {
                    _yandexSearchState.value = InternetSearchState.Error("Не удалось получить перевод от сервера.")
                }
                .onSuccess {
                    _yandexSearchState.value = InternetSearchState.Loaded(it)
                }
        }
        viewModelScope.launch {
            remoteDictionary.searchRusToSrb(russianWord, RemoteTranslatorType.GOOGLE)
                .onFailure {
                    _googleSearchState.value = InternetSearchState.Error("Не удалось получить перевод от сервера.")
                }
                .onSuccess {
                    _googleSearchState.value = InternetSearchState.Loaded(it)
                }
        }
    }

    fun internetSearchSrbToRus(serbianWord: String) {
        internetSearchDirection = InternetSearchDirection.SRB_TO_RUS
        _yandexSearchState.value = InternetSearchState.Loading
        _googleSearchState.value = InternetSearchState.Loading
        viewModelScope.launch {
            remoteDictionary.searchSrbToRus(serbianWord, RemoteTranslatorType.YANDEX)
                .onFailure {
                    _yandexSearchState.value = InternetSearchState.Error("Не удалось получить перевод от сервера.")
                }
                .onSuccess {
                    _yandexSearchState.value = InternetSearchState.Loaded(it)
                }
        }
        viewModelScope.launch {
            remoteDictionary.searchSrbToRus(serbianWord, RemoteTranslatorType.GOOGLE)
                .onFailure {
                    _googleSearchState.value = InternetSearchState.Error("Не удалось получить перевод от сервера.")
                }
                .onSuccess {
                    _googleSearchState.value = InternetSearchState.Loaded(it)
                }
        }
    }

    fun getAddingTranslation(): Translation<Word.Serbian, Word.Russian>? {
        val text = searchingWord.value.text
        if (text.isBlank()) {
            return null
        }

        return when (LanguageDetector.tryDefineLanguage(text)) {

            Language.UNKNOWN -> {
                when (internetSearchDirection) {
                    InternetSearchDirection.UNDEFINE -> {
                        Translation(
                            // take decision that if we can't define language and user not make
                            // internet search, it means that word is Serbian cyrillic.
                            source = Word.Serbian(latinValue = "", cyrillicValue = text),
                            translations = emptyList(),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                    InternetSearchDirection.SRB_TO_RUS -> {
                        Translation(
                            source = Word.Serbian(
                                latinValue = "",
                                cyrillicValue = text
                            ),
                            translations = getRus(),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                    InternetSearchDirection.RUS_TO_SRB -> {
                        Translation(
                            source = Word.Serbian(
                                latinValue = getSrbLat(),
                                cyrillicValue = getSrbCyr()
                            ),
                            translations = listOf(
                                Word.Russian(value = text)
                            ),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                }
            }

            Language.RUSSIAN -> {
                when (internetSearchDirection) {
                    InternetSearchDirection.RUS_TO_SRB -> {
                        Translation(
                            source = Word.Serbian(
                                latinValue = getSrbLat(),
                                cyrillicValue = getSrbCyr()
                            ),
                            translations = listOf(
                                Word.Russian(value = text)
                            ),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                    else -> {
                        Translation(
                            source = Word.Serbian(latinValue = "", cyrillicValue = ""),
                            translations = listOf(
                                Word.Russian(value = text)
                            ),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                }
            }

            Language.SERBIAN_LAT -> {
                when (internetSearchDirection) {
                    InternetSearchDirection.SRB_TO_RUS -> {
                        Translation(
                            source = Word.Serbian(
                                latinValue = text,
                                cyrillicValue = getSrbCyr()
                            ),
                            translations = getRus(),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                    else -> {
                        Translation(
                            source = Word.Serbian(latinValue = text, cyrillicValue = ""),
                            translations = emptyList(),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                }
            }
            Language.SERBIAN_CYR -> {
                when (internetSearchDirection) {
                    InternetSearchDirection.SRB_TO_RUS -> {
                        Translation(
                            source = Word.Serbian(
                                latinValue = getSrbLat(),
                                cyrillicValue = text
                            ),
                            translations = getRus(),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                    else -> {
                        Translation(
                            source = Word.Serbian(latinValue = "", cyrillicValue = text),
                            translations = emptyList(),
                            type = TranslationSourceType.USER,
                            learningStatus = LearningStatus.New()
                        )
                    }
                }
            }
        }
    }

    private fun getSrbLat(): String {
        val yandexState = yandexSearchState.value
        return if (yandexState is InternetSearchState.Loaded) {
            return yandexState.translations.firstOrNull()?.source?.latinValue ?: ""
        } else {
            ""
        }
    }

    private fun getSrbCyr(): String {
        val googleState = googleSearchState.value
        return if (googleState is InternetSearchState.Loaded) {
            return googleState.translations.firstOrNull()?.source?.cyrillicValue ?: ""
        } else {
            ""
        }
    }

    private fun getRus(): List<Word.Russian> {
        val yandexState = yandexSearchState.value
        val googleState = googleSearchState.value
        val result = mutableListOf<Word.Russian>()
        if (yandexState is InternetSearchState.Loaded) {
            result.addAll(yandexState.translations.flatMap {
                it.translations
            })
        }
        if (googleState is InternetSearchState.Loaded) {
            val uniqueGoogleRusWords = googleState.translations.flatMap {
                it.translations
            }.filter { googleRusWord ->
                result.any { yandexRusWord ->
                    !googleRusWord.contentEqual(yandexRusWord)
                }
            }
            result.addAll(uniqueGoogleRusWords)
        }

        return result
    }
}