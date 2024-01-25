package com.trainer.srb.rus

import androidx.datastore.core.DataStore
import com.trainer.srb.rus.core.utils.getDatabaseVersion
import kotlinx.coroutines.flow.firstOrNull

suspend fun DataStore<UserPreferences>.getPredefinedDatabaseVersion(): Int {
    val userPreferences = this.data.firstOrNull()
    return userPreferences?.appVersion.getDatabaseVersion()
}
