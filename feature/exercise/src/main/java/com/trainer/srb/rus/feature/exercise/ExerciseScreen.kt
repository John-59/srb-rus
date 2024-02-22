package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.ui.ConfirmationDialog
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

@Composable
fun ExerciseScreen(
    onFinished: () -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val exerciseStep by viewModel.state.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            if (exerciseStep != ExerciseStep.Initialize
                && exerciseStep != ExerciseStep.ExerciseFinished
                && exerciseStep !is ExerciseStep.Error) {
                ExerciseTopBar(
                    progress = viewModel.progress,
                    onSkip = viewModel::next,
                    onExit = viewModel::confirmExit,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Body(
                exerciseStep = exerciseStep,
                onNext = viewModel::next,
                onAlreadyKnow = viewModel::markAsAlreadyKnow,
                onDontWantLearn = viewModel::markAsNotLearn,
                onFinished = onFinished
            )
        }
    }

    if (viewModel.showExitConfirmation) {
        ConfirmationDialog(
            text = "Закончить упражнение?",
            onCancel = viewModel::hideExitConfirmation,
            onExit = onFinished,
        )
    }

    BackHandler(
        onBack = viewModel::confirmExit
    )
}

@Composable
private fun Body(
    exerciseStep: ExerciseStep,
    onNext: () -> Unit,
    onAlreadyKnow: (translation: Translation<Word.Serbian, Word.Russian>) -> Unit,
    onDontWantLearn: (translation: Translation<Word.Serbian, Word.Russian>) -> Unit,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (exerciseStep) {
        ExerciseStep.Initialize -> {
            InitializeBody(modifier = modifier)
        }

        is ExerciseStep.ShowInRussianAndConstructFromPredefinedLetters -> {
            ShowInRussianAndConstructFromPredefinedLettersBody(
                translation = exerciseStep.translation,
                onNext = onNext,
                modifier = modifier
            )
        }

        is ExerciseStep.ShowInRussianAndSelectSerbianVariants -> {
            ShowInRussianAndSelectSerbianVariantsBody(
                state = exerciseStep,
                onNext = onNext,
                onAlreadyKnow = {
                    onAlreadyKnow(exerciseStep.translation)
                    onNext()
                },
                onDontWantLearn = {
                    onDontWantLearn(exerciseStep.translation)
                    onNext()
                },
                modifier = modifier
            )
        }

        is ExerciseStep.ShowInSerbianAndSelectRussianVariants -> {
            ShowInSerbianAndSelectRussianVariantsBody(
                state = exerciseStep,
                onNext = onNext,
                modifier = modifier
            )
        }

        is ExerciseStep.ShowInRussianAndWriteInSerbian -> {
            ShowInRussianAndWriteInSerbianBody(
                state = exerciseStep,
                onNext = onNext,
                modifier = modifier
            )
        }

        is ExerciseStep.ShowInSerbianWithTranslation -> {
            ShowInSerbianWithTranslationBody(
                translation = exerciseStep.translation,
                onNext = onNext,
                onAlreadyKnow = {
                    onAlreadyKnow(exerciseStep.translation)
                    onNext()
                },
                onDontWantLearn = {
                    onDontWantLearn(exerciseStep.translation)
                    onNext()
                },
                modifier = modifier
            )
        }

        is ExerciseStep.Error -> {
            ErrorBody(
                message = exerciseStep.message,
                modifier = modifier.fillMaxSize()
            )
        }

        ExerciseStep.ExerciseFinished -> {
            FinishBody(
                onNext = onFinished,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun LearnScreenPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ExerciseScreen(
            onFinished = {},
            viewModel = ExerciseViewModel(
                dictionary = DictionaryMock(),
                savedStateHandle = SavedStateHandle(
                    initialState = mapOf(
                        ExerciseArgs.exerciseTypeArgName to ExerciseType.RANDOM.toString()
                    )
                )
            )
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LearnScreenNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ExerciseScreen(
            onFinished = {},
            viewModel = ExerciseViewModel(
                dictionary = DictionaryMock(),
                savedStateHandle = SavedStateHandle(
                    initialState = mapOf(
                        ExerciseArgs.exerciseTypeArgName to ExerciseType.RANDOM.toString()
                    )
                )
            )
        )
    }
}