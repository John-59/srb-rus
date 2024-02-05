package com.trainer.srb.rus.feature.exercise

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

sealed interface Exercise {

    val progress: Float

    val completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>>

    suspend fun next(): ExerciseStep = ExerciseStep.Error("Не задан следующий шаг упражнения")

    fun remove(translation: Translation<Word.Serbian, Word.Russian>) {}

    companion object {
        fun build(
            exerciseType: ExerciseType,
            dictionary: IDictionary,
        ): Exercise {
            return when (exerciseType) {
                ExerciseType.UNDEFINED -> ExerciseUndefined
                ExerciseType.RANDOM -> ExerciseRandom(dictionary)
                ExerciseType.NEW -> ExerciseNew(dictionary)
                ExerciseType.REPEAT -> ExerciseRepeat(dictionary)
            }
        }
    }
}