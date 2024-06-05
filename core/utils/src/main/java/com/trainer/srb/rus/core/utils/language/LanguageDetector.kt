package com.trainer.srb.rus.core.utils.language

object LanguageDetector {

    fun tryDefineLanguage(text: String): Language {
        val letters = text.filter { it.isLetter() }
        return when {
            letters.all {
                SerbianAlphabet.latinLetters.contains(it)
            } -> Language.SERBIAN_LAT

            letters.all {
                SerbianAlphabet.cyrillicLetters.contains(it)
            } && letters.any {
                !RussianAlphabet.letters.contains(it)
            } -> Language.SERBIAN_CYR

            letters.all {
                RussianAlphabet.letters.contains(it)
            } && letters.any {
                !SerbianAlphabet.cyrillicLetters.contains(it)
            } -> Language.RUSSIAN

            else -> Language.UNKNOWN
        }
    }
}