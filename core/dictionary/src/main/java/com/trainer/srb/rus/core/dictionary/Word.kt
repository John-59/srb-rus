package com.trainer.srb.rus.core.dictionary

sealed interface Word {

    fun contains(word: String): Boolean

    fun contentEqual(other: Word): Boolean

    data class Serbian(
        val latinId: Long = 0,
        val cyrillicId: Long = 0,
        val latinValue: String = "",
        val cyrillicValue: String = ""
    ): Word {
        override fun contains(word: String): Boolean {
            return latinValue.contains(word.trim(), ignoreCase = true)
                    || cyrillicValue.contains(word.trim(), ignoreCase = true)
        }

        override fun contentEqual(other: Word): Boolean {
            val otherSerbian = other as? Serbian ?: return false
            return latinValue.trim().equals(otherSerbian.latinValue.trim(), ignoreCase = true)
                    && cyrillicValue.trim().equals(otherSerbian.cyrillicValue.trim(), ignoreCase = true)
        }
    }

    data class Russian(
        val id: Long = 0,
        val value: String = ""
    ): Word {
        override fun contains(word: String): Boolean {
            return value.contains(word.trim(), ignoreCase = true)
        }

        override fun contentEqual(other: Word): Boolean {
            val otherRussian = other as? Russian ?: return false
            return value.trim().equals(otherRussian.value.trim(), ignoreCase = true)
        }
    }
}
