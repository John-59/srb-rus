package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

sealed interface InternetSearchState {

    data object Disabled: InternetSearchState

    data object Loading: InternetSearchState

    data class Error(
        val message: String
    ): InternetSearchState

    data class Loaded(
        val translations: List<Translation<Word.Serbian, Word.Russian>>
    ): InternetSearchState
}