package com.trainer.srb.rus.feature.learn

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.LearningStatusName
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

class ExerciseRandom(
    private val dictionary: IDictionary,
): Exercise {

    private val learningWordsCount = 7

    private val learningSteps = listOf(
        LearningStep.ShowTranslation,
        LearningStep.ChoosingFromSerbianVariants(variantsCount = 4),
//        LearningStep.ChoosingFromRussianVariants(variantsCount = 4),
//        LearningStep.WriteInSerbianFromPredefinedLetters,
        LearningStep.WriteInSerbian
    )

    private val wordToLearningStep = mutableMapOf<Translation<Word.Serbian, Word.Russian>, ArrayDeque<LearningStep>>()

    private var totalStepsCount: Int = 0
    private var _progress: Float = 0f
    override val progress: Float
        get() = _progress

    override suspend fun next(): LearnState {
        if (wordToLearningStep.isEmpty()) {
            val statuses = LearningStatusName.entries.minus(
                listOf(LearningStatusName.ALREADY_KNOW,
                    LearningStatusName.DONT_WANT_LEARN,
                    LearningStatusName.UNUSED)
            ).toTypedArray()
            dictionary.getRandom(learningWordsCount, *statuses).forEach {
                wordToLearningStep[it] = ArrayDeque(learningSteps)
            }
            totalStepsCount = wordToLearningStep.count().coerceAtMost(learningWordsCount) * learningSteps.count()
        }
        return getNextWord().let {
            val (word, stepQueue) = it ?: (null to null)
            step(word, stepQueue).also {
                updateProgress()
            }
        }
    }

    override suspend fun markAsAlreadyKnow(translation: Translation<Word.Serbian, Word.Russian>) {
        wordToLearningStep.remove(translation)
        updateProgress()
        translation.learningStatus = LearningStatus.AlreadyKnow()
        dictionary.update(translation)
    }

    override suspend fun markAsNotLearn(translation: Translation<Word.Serbian, Word.Russian>) {
        wordToLearningStep.remove(translation)
        updateProgress()
        translation.learningStatus = LearningStatus.DontWantLearn()
        dictionary.update(translation)
    }

    private suspend fun step(
        word: Translation<Word.Serbian, Word.Russian>?,
        stepQueue: ArrayDeque<LearningStep>?
    ): LearnState {
        val step = stepQueue?.removeFirstOrNull()
        return if (word == null) {
            LearnState.ExerciseFinished
        } else {
            when (step) {
                is LearningStep.ChoosingFromSerbianVariants -> {
                    val others = dictionary
                        .getRandom(step.variantsCount)
                        .filter {
                            !it.source.latinValue.equals(word.source.latinValue, true)
                        }.take(step.variantsCount - 1)
                    LearnState.ShowInRussianAndSelectSerbianVariants(
                        translation = word,
                        others = others
                    )
                }

                is LearningStep.ChoosingFromRussianVariants -> {
                    LearnState.ShowInSerbianAndSelectRussianVariants()
                }

                LearningStep.ShowTranslation -> {
                    LearnState.ShowInSerbianWithTranslation(word)
                }

                LearningStep.WriteInSerbian -> {
                    LearnState.ShowInRussianAndWriteInSerbian(word)
                }

                LearningStep.WriteInSerbianFromPredefinedLetters -> {
                    LearnState.ShowInRussianAndConstructFromPredefinedLetters(word)
                }

                null -> {
                    LearnState.ExerciseFinished
                }
            }
        }
    }

    private fun getNextWord(): Pair<Translation<Word.Serbian, Word.Russian>, ArrayDeque<LearningStep>>? {
        val randomWord = wordToLearningStep.filter {
            !it.value.isEmpty()
        }.keys.randomOrNull()
        if (randomWord == null) {
            return null
        } else {
            val learningStepQueue = wordToLearningStep[randomWord] ?: return null
            return randomWord to learningStepQueue
        }
    }

    private fun updateProgress() {
        val remainingStepsCount = wordToLearningStep.map {
            it.value.count()
        }.sum()
        _progress = if (totalStepsCount == 0) {
            1f
        } else {
            1 - remainingStepsCount.toFloat() / totalStepsCount
        }
    }
}