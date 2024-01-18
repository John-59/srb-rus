package com.trainer.srb.rus.feature.learn

import com.trainer.srb.rus.core.dictionary.ExerciseType
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.LearningStatus
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
                ExerciseType.UNDEFINED -> Undefined
                ExerciseType.RANDOM -> Random(dictionary)
                ExerciseType.NEW -> TODO()
                ExerciseType.REPEAT -> TODO()
            }
        }
    }

    data object Undefined: Exercise {
        override val progress: Float = 1f
    }

    class Random(
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

        private var _progress: Float = 0f
        override val progress: Float
            get() = _progress

        override suspend fun next(): LearnState {
            if (wordToLearningStep.isEmpty()) {
                dictionary.getRandom(learningWordsCount).forEach {
                    wordToLearningStep[it] = ArrayDeque(learningSteps)
                }
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
            translation.learningStatus = LearningStatus.ALREADY_KNOW
            dictionary.update(translation)
        }

        override suspend fun markAsNotLearn(translation: Translation<Word.Serbian, Word.Russian>) {
            wordToLearningStep.remove(translation)
            updateProgress()
            translation.learningStatus = LearningStatus.DONT_WANT_LEARN
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
            val totalStepsCount = learningWordsCount * learningSteps.count()
            _progress = if (totalStepsCount == 0) {
                1f
            } else {
                1 - remainingStepsCount.toFloat() / totalStepsCount
            }
        }
    }
}