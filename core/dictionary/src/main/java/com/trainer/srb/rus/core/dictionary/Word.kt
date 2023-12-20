package com.trainer.srb.rus.core.dictionary

sealed class Word {
    data class Serbian(val latinValue: String, val cyrillicValue: String): Word()

    class Russian(val value: String): Word()
}
