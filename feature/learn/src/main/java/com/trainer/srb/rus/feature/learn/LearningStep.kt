package com.trainer.srb.rus.feature.learn

sealed class LearningStep {
    data object ShowTranslation: LearningStep()
    data class ChoosingFromSerbianVariants(val variantsCount: Int): LearningStep()
    data object WriteInSerbianFromPredefinedLetters: LearningStep()
    data object WriteInSerbian: LearningStep()
}