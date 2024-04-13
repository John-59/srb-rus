package com.trainer.srb.rus.feature.learn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.exercise.ExerciseRepeat
import com.trainer.srb.rus.core.exercise.ExerciseRepeatAgain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.math.ceil

@HiltViewModel
class LearnViewModel @Inject constructor(
    dictionary: IDictionary
): ViewModel() {

    val isNewWords = dictionary.isNewWords.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )

    val isUnknownWords = dictionary.isUnknownWords.stateIn(
        scope = viewModelScope,
        initialValue = false,
        started = SharingStarted.WhileSubscribed()
    )

    val repeatExercisesCount = flow {
        val wordsCount = dictionary.translationsForRepeat.firstOrNull()?.count() ?: 0
        val exercisesCount = wordsCount.toFloat() / ExerciseRepeat.WORDS_IN_EXERCISE
        emit(ceil(exercisesCount).toInt())
    }.stateIn(
        scope = viewModelScope,
        initialValue = 0,
        started = SharingStarted.WhileSubscribed()
    )

    val repeatAgainExercisesCount = dictionary.translationsForRepeatAgainCount.map {
        val exercisesCount = it.toFloat() / ExerciseRepeatAgain.WORDS_IN_EXERCISE
        ceil(exercisesCount).toInt()
    }.stateIn(
        scope = viewModelScope,
        initialValue = 0,
        started = SharingStarted.WhileSubscribed()
    )
}