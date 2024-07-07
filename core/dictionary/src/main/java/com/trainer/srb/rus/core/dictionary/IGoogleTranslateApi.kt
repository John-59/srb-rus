package com.trainer.srb.rus.core.dictionary

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface IGoogleTranslateApi {

    @POST("v2")
    suspend fun translate(
        @Body translationDto: GoogleTranslationRequestDto,
        @Query("key") apiKey: String = "Google API key was here",
    ): GoogleTranslationResponseDto
}