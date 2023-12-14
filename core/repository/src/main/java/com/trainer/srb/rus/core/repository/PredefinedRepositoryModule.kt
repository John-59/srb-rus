package com.trainer.srb.rus.core.repository

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PredefinedRepositoryModule {

    companion object {
        @Provides
        @Singleton
        fun provideAppDatabaseDao(
            predefinedRepositoryDatabase: PredefinedRepositoryDatabase
        ): PredefinedRepositoryDao {
            return predefinedRepositoryDatabase.predefinedRepositoryDao
        }

        @Provides
        @Singleton
        fun provideAppDatabase(@ApplicationContext context: Context): PredefinedRepositoryDatabase {
            return Room.databaseBuilder(
                context,
                PredefinedRepositoryDatabase::class.java,
                PredefinedRepositoryDatabase.NAME
            ).allowMainThreadQueries().build()
        }
    }

    @Binds
    @Singleton
    abstract fun bindPredefinedRepository(instance: PredefinedRepository): IPredefinedRepository
}