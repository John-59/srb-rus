package com.trainer.srb.rus.feature.repository

sealed class WordStatus {

    protected abstract var innerName: String

    data object Unknown: WordStatus() {
        const val name = "unknown" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object Unused: WordStatus() {
        const val name = "unused" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object New: WordStatus() {
        const val name = "new" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object FirstAcquaintance: WordStatus() {
        const val name = "first_acquaintance" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object NextDay: WordStatus() {
        const val name = "next_day" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object AfterTwoDays: WordStatus() {
        const val name = "after_two_days" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object AfterThreeDays: WordStatus() {
        const val name = "after_three_days" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object AfterWeek: WordStatus() {
        const val name = "after_week" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object AfterTwoWeeks: WordStatus() {
        const val name = "after_two_weeks" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object AfterMonth: WordStatus() {
        const val name = "after_month" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object DontWantLearn: WordStatus() {
        const val name = "dont_want_learn" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    data object AlreadyKnow: WordStatus() {
        const val name = "already_know" // for using in DAO query because only const may be used in DAO
        override var innerName = name
    }

    companion object {
        private val types by lazy {
            WordStatus::class.sealedSubclasses.mapNotNull {
                it.objectInstance
            }
        }

        fun getName(status: WordStatus): String {
            return status.innerName
        }

        fun create(name: String): WordStatus {
            return types.firstOrNull {
                it.innerName.equals(name.trim(), true)
            } ?: Unknown
        }
    }
}