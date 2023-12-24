package com.trainer.srb.rus.feature.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(

): ViewModel() {

    private val _state = MutableStateFlow<LearnState>(LearnState.Initialize)
    val state: StateFlow<LearnState> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = LearnState.Initialize
    )
}