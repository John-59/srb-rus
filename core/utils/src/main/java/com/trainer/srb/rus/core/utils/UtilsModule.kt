package com.trainer.srb.rus.core.utils

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    companion object {
        @Singleton
        @Provides
        fun provideAppVersionProvider(@ApplicationContext context: Context): AppVersionProvider {
            return AppVersionProvider(context)
        }
    }
}