package com.trainer.srb.rus.core.exercise

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.LearningStatusName
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

class ExerciseNew(private val dictionary: IDictionary): Exercise {

    private var totalStepsCount: Int = 0
    private var _progress: Float = 0f
    override val progress: Float
        get() = _progress

    /**
     * All words from exercise.
     */
    private val translations: MutableList<Translation<Word.Serbian, Word.Russian>> = mutableListOf()

    override val completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>>
        get() = wordToCompletedSteps

    private val learningWordsCount = 7

    private val exerciseStepTypes = listOf(
        ExerciseStepType.ShowTranslation,
        ExerciseStepType.ChoosingFromSerbianVariants(variantsCount = 4),
//        LearningStep.ChoosingFromRussianVariants(variantsCount = 4),
//        LearningStep.WriteInSerbianFromPredefinedLetters,
        ExerciseStepType.WriteInSerbian
    )

    private val wordToExerciseStepTypes = mutableMapOf<Translation<Word.Serbian, Word.Russian>, ArrayDeque<ExerciseStepType>>()

    private val wordToCompletedSteps = mutableMapOf<Translation<Word.Serbian, Word.Russian>, MutableList<ExerciseStep>>()

    private var needInit = true

    override suspend fun next(): ExerciseStep {
        if (needInit) {
            dictionary.getRandom(learningWordsCount, LearningStatusName.NEW).forEach {
                translations.add(it)
                wordToExerciseStepTypes[it] = ArrayDeque(exerciseStepTypes)
                wordToCompletedSteps[it] = mutableListOf()
            }
            totalStepsCount = wordToExerciseStepTypes.count().coerceAtMost(learningWordsCount) * exerciseStepTypes.count()
            needInit = false
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
            ExerciseStep.Finished(translations)
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
                    ExerciseStep.Finished(translations)
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