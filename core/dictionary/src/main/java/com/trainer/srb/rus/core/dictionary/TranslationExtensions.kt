package com.trainer.srb.rus.core.dictionary

fun Translation<Word.Serbian, Word.Russian>.serbianAsString(delimiter: String = " / "): String {
    return listOf(source.latinValue, source.cyrillicValue).joinToString(delimiter)
}

fun Translation<Word.Serbian, Word.Russian>.russianAsString(delimiter: String = ", "): String {
    return translations.joinToString(delimiter) {
        it.value
    }
}