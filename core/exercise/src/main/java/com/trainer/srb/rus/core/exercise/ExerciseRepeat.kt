package com.trainer.srb.rus.core.exercise

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.firstOrNull

class ExerciseRepeat(private val dictionary: IDictionary): Exercise {

    companion object {
        const val WORDS_IN_EXERCISE = 7
    }

    private var totalStepsCount: Int = 0
    private var _progress: Float = 0f
    override val progress: Float
        get() = _progress

    override val completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>>
        get() = wordToCompletedSteps

    private val exerciseStepTypes = listOf(
        ExerciseStepType.ChoosingFromSerbianVariants(variantsCount = 4),
//        LearningStep.ChoosingFromRussianVariants(variantsCount = 4),
//        LearningStep.WriteInSerbianFromPredefinedLetters,
        ExerciseStepType.WriteInSerbian
    )

    private val wordToExerciseStepTypes = mutableMapOf<Translation<Word.Serbian, Word.Russian>, ArrayDeque<ExerciseStepType>>()

    private val wordToCompletedSteps = mutableMapOf<Translation<Word.Serbian, Word.Russian>, MutableList<ExerciseStep>>()

    override suspend fun next(): ExerciseStep {
        if (wordToExerciseStepTypes.isEmpty()) {
            val translationsForRepeat = dictionary.translationsForRepeat.firstOrNull()
            translationsForRepeat?.shuffled()?.take(WORDS_IN_EXERCISE)?.forEach {
                wordToExerciseStepTypes[it] = ArrayDeque(exerciseStepTypes)
                wordToCompletedSteps[it] = mutableListOf()
            }
            totalStepsCount = wordToExerciseStepTypes.count().coerceAtMost(WORDS_IN_EXERCISE) * exerciseStepTypes.count()
        }
        return getNextWord().let {
            val (word, stepQueue) = it ?: (null to null)
            step(word, stepQueue).also { exerciseStep ->
                if (word != null) {
                    wordToCompletedSteps[word]?.add(exerciseStep)
                }
                updateProgress()
            }
        }
    }

    override fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        wordToExerciseStepTypes.remove(translation)
        wordToCompletedSteps.remove(translation)
        updateProgress()
    }

    private suspend fun step(
        word: Translation<Word.Serbian, Word.Russian>?,
        stepQueue: ArrayDeque<ExerciseStepType>?
    ): ExerciseStep {
        val step = stepQueue?.removeFirstOrNull()
        return if (word == null) {
            ExerciseStep.ExerciseFinished
        } else {
            when (step) {
                is ExerciseStepType.ChoosingFromSerbianVariants -> {
                    val others = dictionary
                        .getRandom(step.variantsCount)
                        .filter {
                            !it.source.latinValue.equals(word.source.latinValue, true)
                        }.take(step.variantsCount - 1)
                    ExerciseStep.ShowInRussianAndSelectSerbianVariants(
                        translation = word,
                        others = others
                    )
                }

                is ExerciseStepType.ChoosingFromRussianVariants -> {
                    ExerciseStep.ShowInSerbianAndSelectRussianVariants()
                }

                ExerciseStepType.ShowTranslation -> {
                    ExerciseStep.ShowInSerbianWithTranslation(word)
                }

                ExerciseStepType.WriteInSerbian -> {
                    ExerciseStep.ShowInRussianAndWriteInSerbian(word)
                }

                ExerciseStepType.WriteInSerbianFromPredefinedLetters -> {
                    ExerciseStep.ShowInRussianAndConstructFromPredefinedLetters(word)
                }

                null -> {
                    ExerciseStep.ExerciseFinished
                }
            }
        }
    }

    private fun getNextWord(): Pair<Translation<Word.Serbian, Word.Russian>, ArrayDeque<ExerciseStepType>>? {
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

    private fun updateProgress() {
        val remainingStepsCount = wordToExerciseStepTypes.map {
            it.value.count()
        }.sum()
        _progress = if (totalStepsCount == 0) {
            1f
        } else {
            1 - remainingStepsCount.toFloat() / totalStepsCount
        }
    }
}