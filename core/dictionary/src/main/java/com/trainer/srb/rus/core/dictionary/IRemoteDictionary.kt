package com.trainer.srb.rus.core.dictionary

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

interface IRemoteDictionary {
    suspend fun searchRusToSrb(
        russianWord: String,
        translator: RemoteTranslatorType
    ): Result<List<Translation<Word.Serbian, Word.Russian>>>

    suspend fun searchSrbToRus(
        serbianWord: String,
        translator: RemoteTranslatorType
    ): Result<List<Translation<Word.Serbian, Word.Russian>>>
}