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
import com.trainer.srb.rus.core.translation.Word
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
        _yandexSearchState.value = InternetSearchState.Disabled
        _googleSearchState.value = InternetSearchState.Disabled
    }

    fun internetSearchRusToSrb(russianWord: String) {
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
}