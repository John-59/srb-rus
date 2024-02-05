package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.feature.exercise.ExerciseType

@Composable
fun LearnScreen(
    navigateToSearch: () -> Unit,
    navigateToLearn: (ExerciseType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LearnViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Search(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .onFocusChanged {
                    if (it.hasFocus)
                        navigateToSearch()
                }
                .clickable {
                    navigateToSearch()
                },
            value = "",
            onValueChange = {}
        )
        Body(
            navigateToLearn = navigateToLearn,
            viewModel = viewModel,
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        )
    }
}

@Composable
private fun Body(
    navigateToLearn: (ExerciseType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LearnViewModel
) {
    val isNewWords by viewModel.isNewWords.collectAsState()
    val isWordsForRepeat by viewModel.isWordsForRepeat.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Учить слова:",
            color = MainTheme.colors.Border,
            style = MainTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigateToLearn(ExerciseType.RANDOM)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MainTheme.colors.Border),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.White,
            )
        ) {
            Text(
                text = "Случайные слова",
                style = MainTheme.typography.displayMedium
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigateToLearn(ExerciseType.NEW)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MainTheme.colors.Border),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.White,
            ),
            enabled = isNewWords
        ) {
            Text(
                text = "Новые слова",
                style = MainTheme.typography.displayMedium
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = {
                navigateToLearn(ExerciseType.REPEAT)
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MainTheme.colors.Border),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.White,
            ),
            enabled = isWordsForRepeat
        ) {
            Text(
                text = "Повторение слов",
                style = MainTheme.typography.displayMedium
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun HomeScreenPreview() {
    LearnScreen(
        navigateToSearch = {},
        navigateToLearn = {},
        viewModel = LearnViewModel(
            dictionary = DictionaryMock()
        )
    )
}