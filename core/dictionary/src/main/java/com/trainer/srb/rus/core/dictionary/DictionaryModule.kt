package com.trainer.srb.rus.core.dictionary

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DictionaryModule {
    @Binds
    @Singleton
    fun bindInnerDictionary(instance: Dictionary): IDictionary

    @Binds
    @Singleton
    fun bindRemoteDictionary(instance: RemoteDictionary): IRemoteDictionary
}