package com.trainer.srb.rus.feature.dictionary

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
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

    private var _foundRemoteWords: List<Translation<Word.Serbian, Word.Russian>> = emptyList()

    fun searchingWordChange(value: TextFieldValue) {
        searchingWord.value = value
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
    }

    fun internetSearchRusToSrb(russianWord: String) {
        val t = remoteDictionary.searchRusToSrb(russianWord)
//        remoteDictionary.search(
//            Word.Russian(value = russianWord)
//        )
    }

    fun internetSearchSrbToRus(serbianWord: String) {
        val t = remoteDictionary.searchSrbToRus(serbianWord)
//        remoteDictionary.search(
//            Word.Serbian(value = serbianWord)
//        )
    }
}