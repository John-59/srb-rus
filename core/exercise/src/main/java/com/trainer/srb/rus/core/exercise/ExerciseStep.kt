package com.trainer.srb.rus.core.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

sealed class ExerciseStep {

    abstract val result: Boolean

    data class Error(val message: String, override val result: Boolean = false): ExerciseStep()

    data object Initialize: ExerciseStep() {
        override val result: Boolean = true
    }

    data object ExerciseFinished: ExerciseStep() {
        override val result: Boolean = true
    }

    class ShowInSerbianWithTranslation(
        val translation: Translation<Word.Serbian, Word.Russian>,
        override val result: Boolean = true
    ): ExerciseStep()

    class ShowInRussianAndSelectSerbianVariants(
        val translation: Translation<Word.Serbian, Word.Russian>,
        val others: List<Translation<Word.Serbian, Word.Russian>>,
    ): ExerciseStep() {

        override var result: Boolean = false
            private set

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
                result = true
                translationStates[translation] = VariantState.RIGHT
            } else {
                result = false
                translationStates[selectedTranslation] = VariantState.WRONG
                translationStates[translation] = VariantState.RIGHT
            }
        }
    }

    class ShowInSerbianAndSelectRussianVariants: ExerciseStep() {
        override val result: Boolean = true
    }

    class ShowInRussianAndConstructFromPredefinedLetters(
        val translation: Translation<Word.Serbian, Word.Russian>,
        override val result: Boolean = true
    ): ExerciseStep()

    class ShowInRussianAndWriteInSerbian(
        val translation: Translation<Word.Serbian, Word.Russian>
    ): ExerciseStep() {

        override var result: Boolean = false
            private set

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
                translation.source.latinValue.lowercase() -> {
                    result = true
                    Validity.VALID
                }
                translation.source.cyrillicValue.lowercase() -> {
                    result = true
                    Validity.VALID
                }
                else -> {
                    result = false
                    Validity.INVALID
                }
            }
        }
    }
}