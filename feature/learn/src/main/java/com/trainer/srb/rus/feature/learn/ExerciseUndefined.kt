package com.trainer.srb.rus.feature.learn

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

data object ExerciseUndefined: Exercise {
    override val progress: Float = 1f
    override val completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>> = emptyMap()
}