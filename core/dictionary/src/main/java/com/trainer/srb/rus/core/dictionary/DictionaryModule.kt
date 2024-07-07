package com.trainer.srb.rus.core.dictionary

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DictionaryModule {

    @Binds
    @Singleton
    abstract fun bindInnerDictionary(instance: Dictionary): IDictionary

    @Binds
    @Singleton
    abstract fun bindRemoteDictionary(instance: RemoteDictionary): IRemoteDictionary

    companion object {

        private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        @Provides
        @Singleton
        fun provideYandexTranslationApi(): IYandexTranslateApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://translate.api.cloud.yandex.net/translate/v2/")
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()

            return retrofit.create()
        }

        @Provides
        @Singleton
        fun provideGoogleTranslationApi(): IGoogleTranslateApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://translation.googleapis.com/language/translate/")
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .build()

            return retrofit.create()
        }
    }
}