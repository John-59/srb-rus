package com.trainer.srb.rus.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val innerDictionary: IDictionary,
    private val remoteDictionary: IRemoteDictionary
): ViewModel() {

    var searchingWord = MutableStateFlow("")
        private set

    val visibleWords = innerDictionary.translations.combine(searchingWord) { words, word ->
        if (word.isBlank()) {
            words
        } else {
            words.filter {
                it.source.latinValue.startsWith(word, true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed()
    )

    private var _foundRemoteWords: List<Translation<Word.Serbian, Word.Russian>> = emptyList()

    fun searchingWordChange(value: String) {
        searchingWord.value = value
    }

    fun removeTranslation(translation: Translation<Word.Serbian, Word.Russian>) {
        viewModelScope.launch {
            innerDictionary.remove(translation)
        }
    }
}