package com.trainer.srb.rus.core.dictionary

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IYandexTranslateApi {

    @Headers("Authorization: Api-Key Yandex API key was here")
    @POST("translate")
    suspend fun translate(@Body translationDto: YandexTranslationRequestDto): YandexTranslationResponseDto
}