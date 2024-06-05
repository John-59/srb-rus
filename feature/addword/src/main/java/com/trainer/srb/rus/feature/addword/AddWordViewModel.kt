package com.trainer.srb.rus.feature.addword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
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
        *(args.rusValues.split(AddWordArgs.rusValuesSeparator).mapNotNull {
            if (it.isBlank()) {
                null
            } else {
                Word.Russian(value = it)
            }
        }.toTypedArray()),
        Word.Russian()
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
            rusWords[index] = rusWords[index].copy(value = word)
        }
    }

    fun addRusWord(word: String) {
        rusWords[rusWords.lastIndex] = rusWords[rusWords.lastIndex].copy(value = word)
        rusWords.add(Word.Russian())
    }

    fun add() {
        viewModelScope.launch {
            val translation = Translation(
                source = Word.Serbian(
                    latinValue = srbWord.latinValue.trim(),
                    cyrillicValue = srbWord.cyrillicValue.trim()
                ),
                translations = rusWords.filter {
                    it.value.isNotBlank()
                }.map {
                    Word.Russian(
                        value = it.value.trim()
                    )
                },
                type = TranslationSourceType.USER,
                learningStatus = LearningStatus.New()
            )
            dictionary.add(translation)
        }
    }
}