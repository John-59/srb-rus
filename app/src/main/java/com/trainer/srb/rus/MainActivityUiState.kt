package com.trainer.srb.rus

sealed class MainActivityUiState {
    data object Loading: MainActivityUiState()
    data object Success: MainActivityUiState()
}