package com.trainer.srb.rus.feature.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.exercise.Exercise
import com.trainer.srb.rus.core.exercise.ExerciseNew
import com.trainer.srb.rus.core.exercise.ExerciseRandom
import com.trainer.srb.rus.core.exercise.ExerciseRepeat
import com.trainer.srb.rus.core.exercise.ExerciseStep
import com.trainer.srb.rus.core.exercise.ExerciseUndefined
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val dictionary: IDictionary,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val args = ExerciseArgs(savedStateHandle)

    private val exercise = Exercise.build(args.exerciseType, dictionary)

    var progress by mutableFloatStateOf(0f)
        private set

    var showExitConfirmation by mutableStateOf(false)
        private set

    private val _state = MutableStateFlow<ExerciseStepState>(ExerciseStepState.Initialize)
    val state: StateFlow<ExerciseStepState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ExerciseStepState.Initialize
    )

    var showTopBar by mutableStateOf(true)
        private set

    init {
        next()
    }

    fun next() {
        viewModelScope.launch {
            exercise.next().also {
                _state.value = ExerciseStepState.create(it, viewModelScope)
                showTopBar = it != ExerciseStep.Initialize
                    && it !is ExerciseStep.Finished
                    && it !is ExerciseStep.Error
                progress = exercise.progress
                if (it is ExerciseStep.Finished) {
                    updateLearningStatuses()
                }
            }
        }
    }

    fun confirmExit() {
        showExitConfirmation = true
    }

    fun hideExitConfirmation() {
        showExitConfirmation = false
    }

    fun markAsAlreadyKnow(translation: Translation<Word.Serbian, Word.Russian>) {
        viewModelScope.launch {
            exercise.remove(translation)
            translation.learningStatus = LearningStatus.AlreadyKnow()
            dictionary.update(translation)
        }
    }

    fun markAsNotLearn(translation: Translation<Word.Serbian, Word.Russian>) {
        viewModelScope.launch {
            exercise.remove(translation)
            translation.learningStatus = LearningStatus.DontWantLearn()
            dictionary.update(translation)
        }
    }

    private suspend fun updateLearningStatuses() {
        when (exercise) {
            is ExerciseNew -> {
                moveTranslationAlongLearningCurve(exercise.completedSteps)
            }
            is ExerciseRepeat -> {
                moveTranslationAlongLearningCurve(exercise.completedSteps)
            }
            is ExerciseRandom -> {
                exercise.completedSteps.forEach {( translation, _) ->
                    when (translation.learningStatus) {
                        is LearningStatus.New -> {
                            translation.learningStatus = LearningStatus.NextDay()
                            dictionary.update(translation)
                        }
                        is LearningStatus.Unknown -> {
                            translation.learningStatus = LearningStatus.NextDay()
                            dictionary.update(translation)
                        }
                        else -> {}
                    }
                }
            }
            ExerciseUndefined -> {}
        }
    }

    private suspend fun moveTranslationAlongLearningCurve(
        completedSteps: Map<Translation<Word.Serbian, Word.Russian>, List<ExerciseStep>>
    ) {
        completedSteps.forEach { (translation, steps) ->
            when (translation.learningStatus) {
                is LearningStatus.AfterMonth -> {
                    if (steps.all { it.result}) {
                        translation.learningStatus = LearningStatus.AlreadyKnow()
                    } else {
                        translation.learningStatus = LearningStatus.NextDay()
                    }
                }
                is LearningStatus.AfterThreeDays -> {
                    if (steps.all { it.result}) {
                        translation.learningStatus = LearningStatus.AfterWeek()
                    } else {
                        translation.learningStatus = LearningStatus.NextDay()
                    }
                }
                is LearningStatus.AfterTwoDays -> {
                    if (steps.all { it.result}) {
                        translation.learningStatus = LearningStatus.AfterThreeDays()
                    } else {
                        translation.learningStatus = LearningStatus.NextDay()
                    }
                }
                is LearningStatus.AfterTwoWeeks -> {
                    if (steps.all { it.result}) {
                        translation.learningStatus = LearningStatus.AfterMonth()
                    } else {
                        translation.learningStatus = LearningStatus.NextDay()
                    }
                }
                is LearningStatus.AfterWeek -> {
                    if (steps.all { it.result}) {
                        translation.learningStatus = LearningStatus.AfterTwoWeeks()
                    } else {
                        translation.learningStatus = LearningStatus.NextDay()
                    }
                }
                is LearningStatus.New -> {
                    translation.learningStatus = LearningStatus.NextDay()
                }
                is LearningStatus.NextDay -> {
                    if (steps.all { it.result}) {
                        translation.learningStatus = LearningStatus.AfterTwoDays()
                    } else {
                        translation.learningStatus = LearningStatus.NextDay()
                    }
                }
                is LearningStatus.Unknown -> {
                    translation.learningStatus = LearningStatus.NextDay()
                }
                is LearningStatus.AlreadyKnow -> {}
                is LearningStatus.DontWantLearn -> {}
                is LearningStatus.Unused -> {}
            }
            dictionary.update(translation)
        }
    }
}