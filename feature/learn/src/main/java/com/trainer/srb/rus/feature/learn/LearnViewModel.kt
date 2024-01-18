package com.trainer.srb.rus.feature.learn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
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
    dictionary: IDictionary,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val args = LearnArgs(savedStateHandle)

    private val exercise = Exercise.build(args.exerciseType, dictionary)

    var progress by mutableFloatStateOf(0f)
        private set

    var showExitConfirmation by mutableStateOf(false)
        private set

    private val _state = MutableStateFlow<LearnState>(LearnState.Initialize)
    val state: StateFlow<LearnState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LearnState.Initialize
    )

    init {
        next()
    }

    fun next() {
        viewModelScope.launch {
            exercise.next().also {
                _state.value = it
                progress = exercise.progress
            }
        }
    }

    fun showExitConfirmation() {
        showExitConfirmation = true
    }

    fun hideExitConfirmation() {
        showExitConfirmation = false
    }

    fun markAsAlreadyKnow(translation: Translation<Word.Serbian, Word.Russian>) {
        viewModelScope.launch {
            exercise.markAsAlreadyKnow(translation)
        }
    }

    fun markAsNotLearn(translation: Translation<Word.Serbian, Word.Russian>) {
        viewModelScope.launch {
            exercise.markAsNotLearn(translation)
        }
    }
}