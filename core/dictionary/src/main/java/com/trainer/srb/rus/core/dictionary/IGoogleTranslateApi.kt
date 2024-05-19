package com.trainer.srb.rus.core.dictionary

import retrofit2.http.Body
import retrofit2.http.POST

interface IGoogleTranslateApi {

    @POST("v2")
    suspend fun translate(
        @Body translationDto: GoogleTranslationRequestDto,
        @Query("key") apiKey: String = "Google API key was here",
    ): GoogleTranslationResponseDto
}