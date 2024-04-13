package com.trainer.srb.rus.core.translation

/**
 * The wrapper of Serbian latin word id.
 */
sealed interface SerbianLatinWordId {
    /**
     * The Serbian latin word id for the word that the user created.
     */
    data class User(
        val id: Long
    ): SerbianLatinWordId

    /**
     * The Serbian latin word id for the word from the predefined database.
     */
    data class Predefined(
        val id: Long
    ): SerbianLatinWordId
}