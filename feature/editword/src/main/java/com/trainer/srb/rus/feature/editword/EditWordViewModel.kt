package com.trainer.srb.rus.feature.editword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EditWordViewModel @Inject constructor(
    private val dictionary: IDictionary,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val args = EditWordArgs(savedStateHandle)

    val state = flow<EditWordState> {
        val translation = dictionary.get(args.latinValueId)
        val state = if (translation == null) {
            EditWordState.Error("Не найден перевод с идентификатором = ${args.latinValueId}")
        } else {
            EditWordState.Success(
                translation = translation,
                dictionary = dictionary,
                coroutineScope = viewModelScope
            )
        }
        emit(state)
    }.stateIn(
            scope = viewModelScope,
            initialValue = EditWordState.Initialize,
            started = SharingStarted.WhileSubscribed()
    )
}