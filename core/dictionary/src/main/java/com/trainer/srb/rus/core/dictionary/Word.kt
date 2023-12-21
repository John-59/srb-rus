package com.trainer.srb.rus.core.dictionary

sealed class Word {
    data class Serbian(
        val latinId: Long = 0,
        val cyrillicId: Long = 0,
        val latinValue: String = "",
        val cyrillicValue: String = ""
    ): Word()

    data class Russian(
        val id: Long = 0,
        val value: String = ""
    ): Word()
}
