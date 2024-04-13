package com.trainer.srb.rus.core.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The table with information about words that user wants repeat again.
 * @param latId - Id of Serbian latin word.
 * @param isPredefined - True, if latId matches to a word from the predefined database.
 */
@Entity("repeat_again")
data class RepeatAgain(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val latId: Long,
    val isPredefined: Boolean
)
