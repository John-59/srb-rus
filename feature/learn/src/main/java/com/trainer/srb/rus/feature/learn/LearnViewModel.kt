package com.trainer.srb.rus.feature.learn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val dictionary: IDictionary
): ViewModel() {

    private val learningWordsCount = 7

    var progress by mutableFloatStateOf(0f)
        private set

    var showExitConfirmation by mutableStateOf(false)
        private set

    private val learningSteps = listOf(
        LearningStep.ShowTranslation,
        LearningStep.ChoosingFromSerbianVariants(variantsCount = 4),
//        LearningStep.ChoosingFromRussianVariants(variantsCount = 4),
//        LearningStep.WriteInSerbianFromPredefinedLetters,
        LearningStep.WriteInSerbian
    )

    private val _state = MutableStateFlow<LearnState>(LearnState.Initialize)
    val state: StateFlow<LearnState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LearnState.Initialize
    )

    private val wordToLearningStep = mutableMapOf<Translation<Word.Serbian, Word.Russian>, ArrayDeque<LearningStep>>()

    init {
        viewModelScope.launch {
            dictionary.getRandom(learningWordsCount).forEach {
                wordToLearningStep[it] = ArrayDeque(learningSteps)
            }
            next()
        }
    }

    fun next() {
        getNextWord().let {
            val (word, stepQueue) = it ?: (null to null)
            step(word, stepQueue)
        }
    }

    fun showExitConfirmation() {
        showExitConfirmation = true
    }

    fun hideExitConfirmation() {
        showExitConfirmation = false
    }

    private fun step(
        word: Translation<Word.Serbian, Word.Russian>?,
        stepQueue: ArrayDeque<LearningStep>?
    ) {
        val step = stepQueue?.removeFirstOrNull()
        if (word == null) {
            _state.value = LearnState.ExerciseFinished
            progress = 1f
        } else {
            progress += 1f / (learningWordsCount * learningSteps.count())
            when (step) {
                is LearningStep.ChoosingFromSerbianVariants -> {
                    viewModelScope.launch {
                        val others = dictionary
                            .getRandom(step.variantsCount)
                            .filter {
                                !it.source.latinValue.equals(word.source.latinValue, true)
                            }.take(step.variantsCount - 1)
                        _state.value = LearnState.ShowInRussianAndSelectSerbianVariants(
                            translation = word,
                            others = others
                        )
                    }
                }

                is LearningStep.ChoosingFromRussianVariants -> {
                    _state.value = LearnState.ShowInSerbianAndSelectRussianVariants()
                }

                LearningStep.ShowTranslation -> {
                    _state.value = LearnState.ShowInSerbianWithTranslation(word)
                }

                LearningStep.WriteInSerbian -> {
                    _state.value = LearnState.ShowInRussianAndWriteInSerbian(word)
                }

                LearningStep.WriteInSerbianFromPredefinedLetters -> {
                    _state.value = LearnState.ShowInRussianAndConstructFromPredefinedLetters(word)
                }

                null -> {
                    _state.value = LearnState.ExerciseFinished
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
}