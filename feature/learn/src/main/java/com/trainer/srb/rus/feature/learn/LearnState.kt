package com.trainer.srb.rus.feature.learn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

sealed class LearnState {

    data class Error(val message: String): LearnState()

    data object Initialize: LearnState()

    data object ExerciseFinished: LearnState()

    class ShowInSerbianWithTranslation(
        val translation: Translation<Word.Serbian, Word.Russian>
    ): LearnState()

    class ShowInRussianAndSelectSerbianVariants(
        val translation: Translation<Word.Serbian, Word.Russian>,
        val others: List<Translation<Word.Serbian, Word.Russian>>,
    ): LearnState() {

        enum class VariantState {
            ORDINARY,
            RIGHT,
            WRONG
        }

        var selectionIsDone by mutableStateOf(false)
            private set

        val variants = others.toMutableList().apply {
            add(translation)
            shuffle()
        }

        val translationStates = mutableStateMapOf(
            *(variants.map {
                it to VariantState.ORDINARY
            }.toTypedArray())
        )

        fun select(selectedTranslation: Translation<Word.Serbian, Word.Russian>) {
            selectionIsDone = true
            if (selectedTranslation == translation) {
                translationStates[translation] = VariantState.RIGHT
            } else {
                translationStates[selectedTranslation] = VariantState.WRONG
                translationStates[translation] = VariantState.RIGHT
            }
        }
    }

    class ShowInSerbianAndSelectRussianVariants: LearnState()

    class ShowInRussianAndConstructFromPredefinedLetters(
        val translation: Translation<Word.Serbian, Word.Russian>
    ): LearnState()

    class ShowInRussianAndWriteInSerbian(
        val translation: Translation<Word.Serbian, Word.Russian>
    ): LearnState() {

        enum class Validity {
            UNDEFINED,
            VALID,
            INVALID
        }

        var userInput by mutableStateOf("")
            private set

        var userInputValidity by mutableStateOf(Validity.UNDEFINED)
            private set

        fun userInputChanged(value: String) {
            userInput = value
            userInputValidity = Validity.UNDEFINED
        }

        fun checkUserInput() {
            userInputValidity = when (userInput.trim().lowercase()) {
                translation.source.latinValue.lowercase() -> Validity.VALID
                translation.source.cyrillicValue.lowercase() -> Validity.VALID
                else -> Validity.INVALID
            }
        }
    }
}