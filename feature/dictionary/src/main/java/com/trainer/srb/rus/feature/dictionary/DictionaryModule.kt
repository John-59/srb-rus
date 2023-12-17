package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IInnerDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DictionaryModule {
    @Binds
    @Singleton
    fun bindInnerDictionary(instance: InnerDictionary): IInnerDictionary

    @Binds
    @Singleton
    fun bindRemoteDictionary(instance: RemoteDictionary): IRemoteDictionary
}