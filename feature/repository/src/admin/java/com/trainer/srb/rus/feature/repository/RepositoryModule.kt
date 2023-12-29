package com.trainer.srb.rus.feature.repository

import android.content.Context
import androidx.room.Room
import com.trainer.srb.rus.core.repository.IWritableRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    companion object {
        @Provides
        @Singleton
        fun providePredefinedRepositoryDao(
            predefinedRepositoryDatabase: PredefinedRepositoryDatabase
        ): PredefinedRepositoryDao {
            return predefinedRepositoryDatabase.dao
        }

        @Provides
        @Singleton
        fun provideRepositoryDatabase(@ApplicationContext context: Context): PredefinedRepositoryDatabase {
            return Room.databaseBuilder(
                context,
                PredefinedRepositoryDatabase::class.java,
                PredefinedRepositoryDatabase.NAME
            )
                .applyPredefinedMigrations()
                .build()
        }
    }

    @Binds
    @Singleton
    abstract fun bindWritableRepository(instance: WritableRepository): IWritableRepository
}