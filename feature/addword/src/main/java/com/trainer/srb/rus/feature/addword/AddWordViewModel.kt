package com.trainer.srb.rus.feature.addword

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddWordViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val args = AddWordArgs(savedStateHandle)

    var srbLatValue by mutableStateOf(args.srbLatValue)
        private set

    var srbCyrValue by mutableStateOf(args.srbCyrValue)
        private set

    var rusValue by mutableStateOf(args.rusValue)
        private set

    fun srbLatinChange(word: String) {
        srbLatValue = word
    }

    fun srbCyrillicChange(word: String) {
        srbCyrValue = word
    }

    fun rusChange(word: String) {
        rusValue = word
    }

    fun add() {

    }
}