package com.trainer.srb.rus.feature.admin

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddSrbRusTranslationViewModel @Inject constructor(
    val predefinedRepo: IPredefinedRepository
): ViewModel() {

    var srbLatinText by mutableStateOf("")
        private set

    var srbCyrillicText by mutableStateOf("")
        private set

    val rusWords = mutableStateListOf("", "")

    val isAddPossible = derivedStateOf {
        srbLatinText.isNotBlank() && rusWords.any {
            it.isNotBlank()
        }
    }

    fun srbLatinChange(word: String) {
        srbLatinText = word
    }

    fun srbCyrillicChange(word: String) {
        srbCyrillicText = word
    }

    fun addAnotherRussianTranslation(word: String) {
        rusWords[rusWords.lastIndex] = word
        rusWords.add("")
    }

    fun rusChange(index: Int, word: String) {
        rusWords[index] = word
    }

    fun add() {
        val translation = Translation(
            id = -1,
            source = Word.Serbian(
                latinValue = srbLatinText.trim(),
                cyrillicValue = srbCyrillicText.trim()
            ),
            translations = rusWords.filter {
                it.isNotBlank()
            }.map {
                Word.Russian(it.trim())
            }
        )
        predefinedRepo.addSrbToRusTranslation(translation)

        srbLatinText = ""
        srbCyrillicText = ""
        if (rusWords.size > 2) {
            rusWords.removeRange(1, rusWords.lastIndex)
        }
        rusWords[0] = ""
    }
}