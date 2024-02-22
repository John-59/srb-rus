package com.trainer.srb.rus.feature.learn

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
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
    val spacerModifier = Modifier
        .width(5.dp)
        .height(10.dp)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedButton(
            onClick = {
                openExercise(ExerciseType.RANDOM)
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                imageVector = SrIcons.Random,
                contentDescription = null
            )
            Spacer(spacerModifier)
            Text(
                text = "Случайные слова",
                style = MaterialTheme.typography.displayMedium
            )
        }
        Spacer(spacerModifier)
        OutlinedButton(
            onClick = {
                openExercise(ExerciseType.NEW)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isNewWords,
        ) {
            Icon(
                imageVector = SrIcons.New,
                contentDescription = null
            )
            Spacer(spacerModifier)
            Text(
                text = "Новые слова",
                style = MaterialTheme.typography.displayMedium
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = {
                openExercise(ExerciseType.REPEAT)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isWordsForRepeat,
        ) {
            Icon(
                imageVector = SrIcons.Repeat,
                contentDescription = null
            )
            Spacer(spacerModifier)
            Text(
                text = "Повторение слов",
                style = MaterialTheme.typography.displayMedium
            )
        }
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