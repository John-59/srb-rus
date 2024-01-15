package com.trainer.srb.rus.editword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

sealed class EditWordState {

    data object Initialize: EditWordState()

    data class Error(val message: String): EditWordState()

    data class Success(
        private val translation: Translation<Word.Serbian, Word.Russian>,
        private val dictionary: IDictionary,
        private val coroutineScope: CoroutineScope
    ): EditWordState() {

        var srbWord by mutableStateOf(
            translation.source
        )
            private set

        val rusWords = mutableStateListOf(
            *translation.translations.toTypedArray(),
            Word.Russian()
        )

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

        fun edit() {
            coroutineScope.launch {
                val updatedTranslation = Translation(
                    source = Word.Serbian(
                        latinId = translation.source.latinId,
                        latinValue = srbWord.latinValue.trim(),
                        cyrillicId = translation.source.cyrillicId,
                        cyrillicValue = srbWord.cyrillicValue.trim()
                    ),
                    translations = rusWords.filter {
                        it.value.isNotBlank()
                    }.map {
                        if (it.id > 0) {
                            Word.Russian(
                                id = it.id,
                                value = it.value.trim()
                            )
                        } else {
                            Word.Russian(
                                value = it.value.trim()
                            )
                        }
                    },
                    type = translation.type,
                    learningStatus = translation.learningStatus
                )
                dictionary.update(updatedTranslation)
            }
        }
    }
}