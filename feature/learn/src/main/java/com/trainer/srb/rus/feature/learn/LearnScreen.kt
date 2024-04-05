package com.trainer.srb.rus.feature.learn

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.feature.exercise.ExerciseType

@Composable
fun LearnScreen(
    navigateToExercise: (ExerciseType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LearnViewModel = hiltViewModel()
) {
    Surface(
        modifier = modifier
    ) {
        ExerciseSelectionBody(
            openExercise = navigateToExercise,
            viewModel = viewModel,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        )
    }
}

@Composable
private fun ExerciseSelectionBody(
    openExercise: (ExerciseType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LearnViewModel
) {
    val isNewWords by viewModel.isNewWords.collectAsState()
    val isWordsForRepeat by viewModel.isWordsForRepeat.collectAsState()
    val isUnknownWords by viewModel.isUnknownWords.collectAsState()
    val spacerModifier = Modifier
        .width(10.dp)
        .height(10.dp)

    Column(
        modifier = modifier.verticalScroll(
            rememberScrollState()
        ),
        verticalArrangement = Arrangement.Center,
    ) {
        RandomWords(
            openExercise = {
                openExercise(ExerciseType.RANDOM)
            }
        )
        Spacer(spacerModifier)
        UnknownWords(
            enabled = isUnknownWords,
            openExercise = {
                openExercise(ExerciseType.UNKNOWN)
            }
        )
        Spacer(spacerModifier)
        NewWords(
            enabled = isNewWords,
            openExercise = {
                openExercise(ExerciseType.NEW)
            }
        )
        Spacer(spacerModifier)
        RepeatWords(
            enabled = isWordsForRepeat,
            openExercise = {
                openExercise(ExerciseType.REPEAT)
            }
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun LearnScreenPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        LearnScreen(
            viewModel = LearnViewModel(
                dictionary = DictionaryMock()
            ),
            navigateToExercise = {}
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LearnScreenNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        LearnScreen(
            viewModel = LearnViewModel(
                dictionary = DictionaryMock()
            ),
            navigateToExercise = {}
        )
    }
}