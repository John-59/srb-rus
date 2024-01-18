package com.trainer.srb.rus.feature.learn

import com.trainer.srb.rus.core.dictionary.ExerciseType
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

sealed interface Exercise {

    val progress: Float

    suspend fun next(): LearnState = LearnState.Error("Не задан следующий шаг упражнения")

    suspend fun markAsAlreadyKnow(translation: Translation<Word.Serbian, Word.Russian>) {}

    suspend fun markAsNotLearn(translation: Translation<Word.Serbian, Word.Russian>) {}

    companion object {
        fun build(
            exerciseType: ExerciseType,
            dictionary: IDictionary,
        ): Exercise {
            return when (exerciseType) {
                ExerciseType.UNDEFINED -> ExerciseUndefined
                ExerciseType.RANDOM -> ExerciseRandom(dictionary)
                ExerciseType.NEW -> ExerciseNew(dictionary)
                ExerciseType.REPEAT -> TODO()
            }
        }
    }
}