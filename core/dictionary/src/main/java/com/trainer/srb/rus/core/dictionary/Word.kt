package com.trainer.srb.rus.core.dictionary

sealed class Word(
    val value: String
) {
    class Serbian(latinValue: String, val cyrillicValue: String): Word(latinValue)

    class Russian(value: String): Word(value)
}
