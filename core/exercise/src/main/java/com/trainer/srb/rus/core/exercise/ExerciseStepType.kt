package com.trainer.srb.rus.core.exercise

/**
 * Types of steps of exercise.
 */
sealed class ExerciseStepType {
    /**
     * Just show to the user Serbian word and his translation into Russian.
     */
    data object ShowTranslation: ExerciseStepType()

    /**
     * Show Russian word and offer to the user select right Serbian translation.
     */
    data class ChoosingFromSerbianVariants(val variantsCount: Int): ExerciseStepType()

    /**
     * Show Serbian word and offer to the user select right Russian translation.
     */
    data class ChoosingFromRussianVariants(val variantsCount: Int): ExerciseStepType()

    /**
     * Show Russian word and offer to the user write Serbian word from shuffled letters.
     */
    data object WriteInSerbianFromPredefinedLetters: ExerciseStepType()

    /**
     * Show Russian word and offer to the user write Serbian word.
     */
    data object WriteInSerbian: ExerciseStepType()
}