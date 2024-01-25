package com.trainer.srb.rus

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import com.trainer.srb.rus.core.repository.IWritableRepository
import com.trainer.srb.rus.core.utils.AppVersionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val predefinedRepository: IPredefinedRepository,
    private val writableRepository: IWritableRepository,
    private val dataStore: DataStore<UserPreferences>,
    private val appVersionProvider: AppVersionProvider
): ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = writableRepository.predefinedStatuses.map {
        if (needPredefinedStatusesUpdate()) {
            predefinedRepository.resetStatuses()
            it.forEach { (srbLatinId, status) ->
                predefinedRepository.setStatusById(srbLatinId, status)
            }
        }
        updateAppVersion()
        MainActivityUiState.Success
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed()
    )

    private suspend fun needPredefinedStatusesUpdate(): Boolean {
        return dataStore.getPredefinedDatabaseVersion() < appVersionProvider.predefinedDatabaseVersion
    }

    private suspend fun updateAppVersion() {
        dataStore.updateData {
            it.toBuilder().setAppVersion(appVersionProvider.version).build()
        }
    }
}