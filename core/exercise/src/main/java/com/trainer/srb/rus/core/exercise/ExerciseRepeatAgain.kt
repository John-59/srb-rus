package com.trainer.srb.rus.core.exercise

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.flow.firstOrNull

class ExerciseRepeatAgain(private val dictionary: IDictionary): Exercise() {

    companion object {
        const val WORDS_IN_EXERCISE = 7
    }

    /**
     * All words from exercise.
     */
    private val translations: MutableList<Translation<Word.Serbian, Word.Russian>> = mutableListOf()

    private val exerciseStepTypes = listOf(
        ExerciseStepType.ChoosingFromSerbianVariants(variantsCount = 4),
        ExerciseStepType.WriteInSerbian
    )

    override suspend fun next(): ExerciseStep {
        if (wordToExerciseStepTypes.isEmpty()) {
            val translationsForRepeat = dictionary.translationsForRepeatAgain.firstOrNull()
            translationsForRepeat?.shuffled()?.take(ExerciseRepeat.WORDS_IN_EXERCISE)?.forEach {
                translations.add(it)
                wordToExerciseStepTypes[it] = ArrayDeque(exerciseStepTypes)
                wordToCompletedSteps[it] = mutableListOf()
            }
            totalStepsCount = wordToExerciseStepTypes.count().coerceAtMost(ExerciseRepeat.WORDS_IN_EXERCISE) * exerciseStepTypes.count()
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
}