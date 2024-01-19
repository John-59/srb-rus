package com.trainer.srb.rus.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    dictionary: IDictionary
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
}