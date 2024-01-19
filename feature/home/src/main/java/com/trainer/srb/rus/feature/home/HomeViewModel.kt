package com.trainer.srb.rus.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dictionary: IDictionary
): ViewModel() {

    val isNewWords = dictionary.isNewWords.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )

    var isWordsForRepeat by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            isWordsForRepeat = dictionary.containsWordsForRepeat()
        }
    }
}