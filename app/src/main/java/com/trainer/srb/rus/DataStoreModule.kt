package com.trainer.srb.rus

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val DATA_STORE_FILE_NAME = "user_prefs.pb"

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<UserPreferences> {
        return DataStoreFactory.create(
            serializer = UserPreferencesSerializer,
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { UserPreferences.newBuilder().build() }
            ),
            produceFile = { context.dataStoreFile(DATA_STORE_FILE_NAME) }
        )
    }
}