package com.trainer.srb.rus.feature.learn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.feature.exercise.ExerciseState
import com.trainer.srb.rus.feature.exercise.ExerciseType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val dictionary: IDictionary
): ViewModel() {

    val isNewWords = dictionary.isNewWords.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )

    var isWordsForRepeat = dictionary.isWordsForRepeat.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )

    var exerciseState by mutableStateOf<ExerciseState?>(null)
        private set

    fun openExercise(exerciseType: ExerciseType) {
        exerciseState = ExerciseState(
            dictionary = dictionary,
//            exerciseType = exerciseType
        )
    }

    fun closeExercise() {
        exerciseState = null
    }
}