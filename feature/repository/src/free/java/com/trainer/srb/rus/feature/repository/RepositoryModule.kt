package com.trainer.srb.rus.feature.repository

import android.content.Context
import androidx.room.Room
import com.trainer.srb.rus.core.repository.IPredefinedRepository
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
        fun providePredefinedDatabaseDao(
            predefinedRepositoryDatabase: PredefinedRepositoryDatabase
        ): PredefinedRepositoryDao {
            return predefinedRepositoryDatabase.dao
        }

        @Provides
        @Singleton
        fun providePredefinedDatabase(@ApplicationContext context: Context): PredefinedRepositoryDatabase {
            return Room.databaseBuilder(
                context,
                PredefinedRepositoryDatabase::class.java,
                PredefinedRepositoryDatabase.NAME
            )
                .createFromAsset(PredefinedRepositoryDatabase.NAME)
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        @Singleton
        fun provideInnerDatabaseDao(
            innerRepositoryDatabase: InnerRepositoryDatabase
        ): InnerRepositoryDao {
            return innerRepositoryDatabase.dao
        }

        @Provides
        @Singleton
        fun provideInnerDatabase(@ApplicationContext context: Context): InnerRepositoryDatabase {
            return Room.databaseBuilder(
                context,
                InnerRepositoryDatabase::class.java,
                InnerRepositoryDatabase.NAME
            )
                .build()
        }
    }

    @Binds
    @Singleton
    abstract fun bindPredefinedRepository(instance: PredefinedRepository): IPredefinedRepository

    @Binds
    @Singleton
    abstract fun bindWritableRepository(instance: WritableRepository): IWritableRepository
}