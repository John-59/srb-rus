package com.trainer.srb.rus.feature.learn

import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

sealed class LearnState {

    data class Error(val message: String): LearnState()

    data object Initialize: LearnState()

    class ShowInSerbianWithTranslation(
        val translation: Translation<Word.Serbian, Word.Russian>
    ): LearnState()

    class ShowInRussianAndSelectSerbianVariants: LearnState()

    class ShowInRussianAndConstructFromPredefinedLetters: LearnState()

    class ShowInRussianAndWriteInSerbian: LearnState()
}