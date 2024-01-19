package com.trainer.srb.rus.feature.dictionary

import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.LearningStatusName
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IWritableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import javax.inject.Inject

class Dictionary @Inject constructor(
    private val writableRepository: IWritableRepository
): IDictionary {

    override val translations = writableRepository.translations

    override val isNewWords: Flow<Boolean> = translations.transform {
        val containsNew = it.any {  translation ->
            translation.learningStatus is LearningStatus.New
        }
        emit(containsNew)
    }

    override val isWordsForRepeat: Flow<Boolean> = translations.transform {
        val now = Clock.System.now()
        val containsWordsForRepeat = it.any { translation ->
            canRepeat(translation, now)
        }
        emit(containsWordsForRepeat)
    }

    override suspend fun get(serbianLatinId: Long): Translation<Word.Serbian, Word.Russian>? {
        return writableRepository.get(serbianLatinId)
    }

    override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
        return writableRepository.search(value)
    }

    override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
        // for admin version all new words checked as unknown in order to users learning
        // such words as random word from predefined dictionary.
        translation.learningStatus = LearningStatus.Unknown()
        writableRepository.add(translation)
    }

    override suspend fun edit(translation: Translation<Word.Serbian, Word.Russian>) {
        update(translation)
    }

    override suspend fun update(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.update(translation)
    }

    override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
        writableRepository.remove(translation)
    }

    override suspend fun getRandom(
        randomTranslationsCount: Int,
        vararg statuses: LearningStatusName
    ): List<Translation<Word.Serbian, Word.Russian>> {
        val usedStatuses = if (statuses.isEmpty()) {
            LearningStatusName.entries.toList()
        } else {
            statuses.toList()
        }
        return writableRepository.getRandom(randomTranslationsCount, usedStatuses)
    }

    private fun canRepeat(translation: Translation<Word.Serbian, Word.Russian>, now: Instant): Boolean {
        val status = translation.learningStatus
        val statusMidnight = LocalDateTime(
            year = status.dateTime.year,
            month = status.dateTime.month,
            dayOfMonth = status.dateTime.dayOfMonth,
            hour = status.dateTime.hour,
            minute = status.dateTime.minute,
            second = status.dateTime.second,
            nanosecond = status.dateTime.nanosecond
        ).toInstant(TimeZone.currentSystemDefault())
        when (status) {
            is LearningStatus.AfterMonth -> {
                return (now - statusMidnight).inWholeDays >= 30
            }
            is LearningStatus.AfterThreeDays -> {
                return (now - statusMidnight).inWholeDays >= 3
            }
            is LearningStatus.AfterTwoDays -> {
                return (now - statusMidnight).inWholeDays >= 2
            }
            is LearningStatus.AfterTwoWeeks -> {
                return (now - statusMidnight).inWholeDays >= 14
            }
            is LearningStatus.AfterWeek -> {
                return (now - statusMidnight).inWholeDays >= 7
            }
            is LearningStatus.AlreadyKnow -> return false
            is LearningStatus.DontWantLearn -> return false
            is LearningStatus.New -> return false
            is LearningStatus.NextDay -> {
                return (now - statusMidnight).inWholeDays >= 1
            }
            is LearningStatus.Unknown -> return false
            is LearningStatus.Unused -> return false
        }
    }
}