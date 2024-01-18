package com.trainer.srb.rus.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.LearningStatusName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dictionary: IDictionary
): ViewModel() {

    var isNewWords by mutableStateOf(false)
        private set

    var isWordsForRepeat by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            isNewWords = dictionary.getRandom(1, LearningStatusName.NEW).isNotEmpty()
        }
        viewModelScope.launch {
            isWordsForRepeat = dictionary.containsWordsForRepeat()
        }
    }
}