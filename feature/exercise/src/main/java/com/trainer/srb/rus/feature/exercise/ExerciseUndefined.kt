package com.trainer.srb.rus.feature.exercise

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

data object ExerciseUndefined: Exercise {
    override val progress: Float = 1f
    override val completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>> = emptyMap()
}