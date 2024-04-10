package com.trainer.srb.rus.feature.exercise

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.exercise.ExerciseType
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.ui.ConfirmationDialog

@Composable
fun ExerciseScreen(
    onFinished: () -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val exerciseStepState by viewModel.state.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            if (viewModel.showTopBar) {
                ExerciseTopBar(
                    progress = viewModel.progress,
                    onSkip = viewModel::next,
                    onExit = viewModel::confirmExit,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
            Body(
                exerciseStepState = exerciseStepState,
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
    exerciseStepState: ExerciseStepState,
    onNext: () -> Unit,
    onAlreadyKnow: (translation: Translation<Word.Serbian, Word.Russian>) -> Unit,
    onDontWantLearn: (translation: Translation<Word.Serbian, Word.Russian>) -> Unit,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (exerciseStepState) {
        ExerciseStepState.Initialize -> {
            InitializeBody(modifier = modifier)
        }

        is ExerciseStepState.ShowInRussianAndConstructFromPredefinedLetters -> {
            ShowInRussianAndConstructFromPredefinedLettersBody(
                state = exerciseStepState,
                onNext = onNext,
                modifier = modifier
            )
        }

        is ExerciseStepState.ShowInRussianAndSelectSerbianVariants -> {
            ShowInRussianAndSelectSerbianVariantsBody(
                state = exerciseStepState,
                onNext = onNext,
                onAlreadyKnow = {
                    onAlreadyKnow(exerciseStepState.translation)
                    onNext()
                },
                onDontWantLearn = {
                    onDontWantLearn(exerciseStepState.translation)
                    onNext()
                },
                modifier = modifier
            )
        }

        is ExerciseStepState.ShowInSerbianAndSelectRussianVariants -> {
            ShowInSerbianAndSelectRussianVariantsBody(
                state = exerciseStepState,
                onNext = onNext,
                modifier = modifier
            )
        }

        is ExerciseStepState.ShowInRussianAndWriteInSerbian -> {
            ShowInRussianAndWriteInSerbianBody(
                state = exerciseStepState,
                onNext = onNext,
                modifier = modifier
            )
        }

        is ExerciseStepState.ShowInSerbianWithTranslation -> {
            ShowInSerbianWithTranslationBody(
                state = exerciseStepState,
                onNext = onNext,
                onAlreadyKnow = {
                    onAlreadyKnow(exerciseStepState.translation)
                    onNext()
                },
                onDontWantLearn = {
                    onDontWantLearn(exerciseStepState.translation)
                    onNext()
                },
                modifier = modifier
            )
        }

        is ExerciseStepState.Error -> {
            ErrorBody(
                state = exerciseStepState,
                modifier = modifier.fillMaxSize()
            )
        }

        is ExerciseStepState.Finished -> {
            FinishBody(
                state = exerciseStepState,
                onNext = onFinished,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@PreviewLightDark
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