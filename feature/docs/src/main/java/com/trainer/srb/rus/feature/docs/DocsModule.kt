package com.trainer.srb.rus.feature.docs

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DocsModule {

    @Singleton
    @Provides
    fun provideMdReader(@ApplicationContext context: Context): IMdReader {
        return MdReaderFromAssets(context)
    }
}