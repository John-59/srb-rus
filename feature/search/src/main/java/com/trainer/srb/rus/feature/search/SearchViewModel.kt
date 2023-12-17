package com.trainer.srb.rus.feature.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IInnerDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val innerDictionary: IInnerDictionary,
    private val remoteDictionary: IRemoteDictionary
): ViewModel() {

    private val _innerWords = MutableStateFlow<List<Translation<Word.Serbian, Word.Russian>>>(
        emptyList()
    )
    val innerWords = _innerWords.stateIn(
        scope = viewModelScope,
        initialValue = emptyList(),
        started = SharingStarted.WhileSubscribed()
    )

    private var _foundRemoteWords: List<Translation<Word.Serbian, Word.Russian>> = emptyList()

    var searchingWord by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            _innerWords.value = innerDictionary.getAllByAlphabet()
        }
    }

    fun searchingWordChange(value: String) {
        searchingWord = value

        viewModelScope.launch {
            if (value.isBlank()) {
                _innerWords.value = innerDictionary.getAllByAlphabet()
            } else {
                _innerWords.value = innerDictionary.search(value)
            }
        }
//        viewModelScope.launch {
//            _foundRemoteWords = remoteDictionary.search(value)
//        }
    }
}