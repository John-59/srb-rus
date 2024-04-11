package com.trainer.srb.rus.feature.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.trainer.srb.rus.core.exercise.ExerciseStep
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.russianAsString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

/**
 * UI-state of exercise step.
 */
sealed class ExerciseStepState {

    companion object {
        /**
         * Create UI-state by step.
         */
        fun create(
            step: ExerciseStep,
            coroutineScope: CoroutineScope
        ): ExerciseStepState {
            return when (step) {
                is ExerciseStep.Error -> {
                    Error(step)
                }
                is ExerciseStep.Finished -> {
                    Finished(step, coroutineScope)
                }
                ExerciseStep.Initialize -> {
                    Initialize
                }
                is ExerciseStep.ShowInRussianAndConstructFromPredefinedLetters -> {
                    ShowInRussianAndConstructFromPredefinedLetters(step)
                }
                is ExerciseStep.ShowInRussianAndSelectSerbianVariants -> {
                    ShowInRussianAndSelectSerbianVariants(step)
                }
                is ExerciseStep.ShowInRussianAndWriteInSerbian -> {
                    ShowInRussianAndWriteInSerbian(step)
                }
                is ExerciseStep.ShowInSerbianAndSelectRussianVariants -> {
                    ShowInSerbianAndSelectRussianVariants(step)
                }
                is ExerciseStep.ShowInSerbianWithTranslation -> {
                    ShowInSerbianWithTranslation(step)
                }
            }
        }
    }

    /**
     * The exercise is initialized.
     */
    data object Initialize: ExerciseStepState()

    /**
     * An error occurred during exercise (the application error, not user).
     */
    data class Error(private val step: ExerciseStep.Error): ExerciseStepState() {

        /**
         * The error message.
         */
        val message = step.message
    }

    /**
     * The exercise is finished.
     */
    class Finished(
        step: ExerciseStep.Finished,
        coroutineScope: CoroutineScope
    ): ExerciseStepState() {

        private val _learnedWords = MutableStateFlow(step.translations)

        /**
         * Words that were learned in the exercise.
         */
        val learnedWords = _learnedWords.stateIn(
            scope = coroutineScope,
            initialValue = step.translations,
            started = SharingStarted.WhileSubscribed()
        )

        private val _repeatAgainWords =
            MutableStateFlow<List<Translation<Word.Serbian, Word.Russian>>>(
                emptyList()
            )

        /**
         * Words selected by the user to repeat again.
         */
        val repeatAgainWords = _repeatAgainWords.stateIn(
            scope = coroutineScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed()
        )

        /**
         * Add the translation to exercise "Repeat again".
         */
        fun repeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
            _learnedWords.value -= translation
            _repeatAgainWords.value += translation
        }

        /**
         * Remove the translation from the list of words that the user wants to repeat again.
         */
        fun notRepeatAgain(translation: Translation<Word.Serbian, Word.Russian>) {
            _learnedWords.value += translation
            _repeatAgainWords.value -= translation
        }
    }

    /**
     * Show word in Serbian and his translation to Russian.
     */
    class ShowInSerbianWithTranslation(
        step: ExerciseStep.ShowInSerbianWithTranslation
    ): ExerciseStepState() {

        /**
         * The translation for which the step is.
         */
        val translation = step.translation
    }

    /**
     * Show word in Russian and offer to select right Serbian translation from several variants.
     */
    class ShowInRussianAndSelectSerbianVariants(
        private val step: ExerciseStep.ShowInRussianAndSelectSerbianVariants
    ): ExerciseStepState() {

        /**
         * The translation for which the step is.
         */
        val translation = step.translation

        /**
         * Russian word.
         */
        val russian = step.translation.russianAsString()

        /**
         * Variants of translation.
         */
        val variants: List<Translation<Word.Serbian, Word.Russian>> = step.others.toMutableList()
            .apply {
                add(step.translation)
                shuffle()
            }

        /**
         * Did user select one of translation variants?
         */
        var selectionIsDone by mutableStateOf(false)
            private set

        private enum class VariantState {
            UNDEFINED,
            RIGHT,
            WRONG
        }

        private val translationStates = mutableStateMapOf(
            *(variants.map {
                it to VariantState.UNDEFINED
            }.toTypedArray())
        )

        /**
         * Select one variant from offered.
         */
        fun select(selectedTranslation: Translation<Word.Serbian, Word.Russian>) {
            selectionIsDone = true
            if (selectedTranslation == step.translation) {
                step.result = true
                translationStates[step.translation] = VariantState.RIGHT
            } else {
                step.result = false
                translationStates[selectedTranslation] = VariantState.WRONG
                translationStates[step.translation] = VariantState.RIGHT
            }
        }

        /**
         * Is the translation right?
         * (Until the user makes selection, all translations have the UNDEFINED statuses)
         */
        fun isValid(translation: Translation<Word.Serbian, Word.Russian>): Boolean {
            return translationStates[translation] == VariantState.RIGHT
        }

        /**
         * Is the translation wrong?
         * (Until the user makes selection, all translations have the UNDEFINED statuses)
         */
        fun isWrong(translation: Translation<Word.Serbian, Word.Russian>): Boolean {
            return translationStates[translation] == VariantState.WRONG
        }
    }

    /**
     * Show Russian word and offer to write it in Serbian.
     */
    class ShowInRussianAndWriteInSerbian(
        private val step: ExerciseStep.ShowInRussianAndWriteInSerbian
    ): ExerciseStepState() {

        /**
         * The translation for which the step is.
         */
        val translation = step.translation

        /**
         * The string that user wrote.
         */
        var userInput by mutableStateOf("")
            private set

        /**
         * Is user input correct?
         * Null, if the user input has not checked yet.
         */
        var validity by mutableStateOf<Boolean?>(null)
            private set

        /**
         * User input handler.
         */
        fun onUserInputChanged(value: String) {
            userInput = value
            validity = null
        }

        /**
         * Validate user input.
         */
        fun validateUserInput() {
            validity = when (userInput.trim().lowercase()) {
                translation.source.latinValue.lowercase() -> {
                    step.result = true
                    true
                }
                translation.source.cyrillicValue.lowercase() -> {
                    step.result = true
                    true
                }
                else -> {
                    step.result = false
                    false
                }
            }
        }
    }

    /**
     * Show word in Russian language and offer to construct Serbian word from some letters.
     * (not done yet)
     */
    class ShowInRussianAndConstructFromPredefinedLetters(
        step: ExerciseStep.ShowInRussianAndConstructFromPredefinedLetters
    ): ExerciseStepState()

    /**
     * Show word in Serbian language and offer to select right translation from several
     * Russian words.
     * (not done yet)
     */
    class ShowInSerbianAndSelectRussianVariants(
        step: ExerciseStep.ShowInSerbianAndSelectRussianVariants
    ): ExerciseStepState()
}