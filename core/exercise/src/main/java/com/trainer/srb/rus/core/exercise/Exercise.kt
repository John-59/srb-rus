package com.trainer.srb.rus.core.exercise

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

/**
 * The exercise.
 */
sealed class Exercise {

    /**
     * All the words from the exercise, each mapped to the required steps for that word.
     * (For different types of exercise required steps could be different)
     */
    protected val wordToExerciseStepTypes = mutableMapOf<Translation<Word.Serbian, Word.Russian>, ArrayDeque<ExerciseStepType>>()

    /**
     * The words from exercise, each mapped to the steps that the user done.
     * Each step could be done with successful result or not, but in both cases step is done.
     */
    protected val wordToCompletedSteps = mutableMapOf<Translation<Word.Serbian, Word.Russian>, MutableList<ExerciseStep>>()

    /**
     * The words from exercise, each mapped to the steps that the user done.
     * Each step could be done with successful result or not, but in both cases step is done.
     */
    val completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>> = wordToCompletedSteps

    /**
     * The progress of the exercise execution (between 0 and 1).
     */
    var progress: Float = 0f
        private set

    /**
     * The total steps amount in the whole exercise.
     * Initially equal to the words count multiply by the steps count, but it must be
     * recalculated if the user excludes some words from the exercise (for example, selects
     * "Already know this word" or "Don't wont to learn this word).
     */
    protected var totalStepsCount: Int = 0

    /**
     * Remove the word from the exercise.
     */
    fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        wordToExerciseStepTypes.remove(translation)
        wordToCompletedSteps.remove(translation)
        updateProgress()
    }

    protected fun updateProgress() {
        val remainingStepsCount = wordToExerciseStepTypes.map {
            it.value.count()
        }.sum()
        progress = if (totalStepsCount == 0) {
            1f
        } else {
            1 - remainingStepsCount.toFloat() / totalStepsCount
        }
    }

    protected fun getNextWord(): Pair<Translation<Word.Serbian, Word.Russian>, ArrayDeque<ExerciseStepType>>? {
        val randomWord = wordToExerciseStepTypes.filter {
            !it.value.isEmpty()
        }.keys.randomOrNull()
        if (randomWord == null) {
            return null
        } else {
            val learningStepQueue = wordToExerciseStepTypes[randomWord] ?: return null
            return randomWord to learningStepQueue
        }
    }

    abstract suspend fun next(): ExerciseStep

    companion object {
        fun build(
            exerciseType: ExerciseType,
            dictionary: IDictionary,
        ): Exercise {
            return when (exerciseType) {
                ExerciseType.UNDEFINED -> ExerciseUndefined
                ExerciseType.RANDOM -> {
                    val statuses = LearningStatusName.entries.minus(
                        listOf(
                            LearningStatusName.ALREADY_KNOW,
                            LearningStatusName.DONT_WANT_LEARN,
                            LearningStatusName.UNUSED)
                    ).toTypedArray()
                    ExerciseRandom(dictionary, statuses)
                }
                ExerciseType.NEW -> ExerciseNew(dictionary)
                ExerciseType.REPEAT -> ExerciseRepeat(dictionary)
                ExerciseType.UNKNOWN -> {
                    val statuses = arrayOf(
                        LearningStatusName.UNKNOWN
                    )
                    ExerciseRandom(dictionary, statuses)
                }
                ExerciseType.REPEAT_AGAIN -> ExerciseRepeatAgain(dictionary)
            }
        }
    }
}