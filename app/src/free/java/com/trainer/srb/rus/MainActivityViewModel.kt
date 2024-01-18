package com.trainer.srb.rus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val predefinedRepository: IPredefinedRepository,
    private val writableRepository: IWritableRepository
): ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = writableRepository.predefinedStatuses.map {
        it.forEach { (srbLatinId, status) ->
            predefinedRepository.setStatusById(srbLatinId, status)
        }
        MainActivityUiState.Success
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed()
    )
}