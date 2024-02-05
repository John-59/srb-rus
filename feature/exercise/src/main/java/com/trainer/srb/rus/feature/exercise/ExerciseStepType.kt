package com.trainer.srb.rus.feature.exercise

sealed class ExerciseStepType {
    data object ShowTranslation: ExerciseStepType()
    data class ChoosingFromSerbianVariants(val variantsCount: Int): ExerciseStepType()
    data class ChoosingFromRussianVariants(val variantsCount: Int): ExerciseStepType()
    data object WriteInSerbianFromPredefinedLetters: ExerciseStepType()
    data object WriteInSerbian: ExerciseStepType()
}