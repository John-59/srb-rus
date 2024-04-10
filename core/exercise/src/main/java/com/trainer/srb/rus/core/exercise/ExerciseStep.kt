package com.trainer.srb.rus.core.exercise

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

/**
 * The step of the exercise.
 * @param result Result of step execution by user.
 */
sealed class ExerciseStep(
    var result: Boolean = true
) {

    /**
     * Internal error (an application error, not user).
     */
    data class Error(val message: String): ExerciseStep()

    /**
     * The exercise is initializing.
     */
    data object Initialize: ExerciseStep()

    /**
     * Step "Exercise finished"
     * @param translations All words from entire exercise.
     */
    class Finished(
        val translations: List<Translation<Word.Serbian, Word.Russian>>,
    ): ExerciseStep()

    /**
     * Show word in Serbian and his translation to Russian.
     */
    class ShowInSerbianWithTranslation(
        val translation: Translation<Word.Serbian, Word.Russian>,
    ): ExerciseStep()

    /**
     * Show word in Russian and offer to select right Serbian translation from several variants.
     */
    class ShowInRussianAndSelectSerbianVariants(
        val translation: Translation<Word.Serbian, Word.Russian>,
        val others: List<Translation<Word.Serbian, Word.Russian>>,
    ): ExerciseStep()

    /**
     * Show word in Serbian language and offer to select right translation from several
     * Russian words.
     * (not done yet)
     */
    class ShowInSerbianAndSelectRussianVariants: ExerciseStep()

    /**
     * Show word in Russian language and offer to construct Serbian word from some letters.
     * (not done yet)
     */
    class ShowInRussianAndConstructFromPredefinedLetters(
        val translation: Translation<Word.Serbian, Word.Russian>,
    ): ExerciseStep()

    /**
     * Show Russian word and offer to write it in Serbian.
     */
    class ShowInRussianAndWriteInSerbian(
        val translation: Translation<Word.Serbian, Word.Russian>
    ): ExerciseStep()
}