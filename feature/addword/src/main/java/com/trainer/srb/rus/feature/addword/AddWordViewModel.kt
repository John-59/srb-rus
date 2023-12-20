package com.trainer.srb.rus.feature.addword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddWordViewModel @Inject constructor(
    private val dictionary: IDictionary,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val args = AddWordArgs(savedStateHandle)

    val rusWords = mutableStateListOf(
        Word.Russian(args.rusValue),
        Word.Russian("")
    )

    var srbWord by mutableStateOf(
        Word.Serbian(
            latinValue = args.srbLatValue,
            cyrillicValue = args.srbCyrValue
        )
    )
        private set

    fun srbLatinChange(word: String) {
        srbWord = srbWord.copy(
            latinValue = word
        )
    }

    fun srbCyrillicChange(word: String) {
        srbWord = srbWord.copy(
            cyrillicValue = word
        )
    }

    fun rusChange(index: Int, word: String) {
        if (index in rusWords.indices) {
            rusWords[index] = Word.Russian(word)
        }
    }

    fun addRusWord(word: String) {
        rusWords[rusWords.lastIndex] = Word.Russian(word)
        rusWords.add(Word.Russian(""))
    }

    fun add() {
        viewModelScope.launch {
            val translation = Translation(
                id = 0,
                source = srbWord,
                translations = rusWords.filter {
                    it.value.isNotBlank()
                }
            )
            dictionary.add(translation)
        }
    }
}