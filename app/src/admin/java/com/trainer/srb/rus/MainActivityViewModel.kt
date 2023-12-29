package com.trainer.srb.rus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(): ViewModel() {

    val uiState = emptyFlow<MainActivityUiState>().stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Success,
        started = SharingStarted.WhileSubscribed()
    )
}