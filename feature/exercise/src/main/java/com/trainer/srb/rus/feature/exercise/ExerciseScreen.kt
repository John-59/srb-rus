package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.ui.ExitExerciseConfirmationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    onFinished: () -> Unit,
    exerciseState: ExerciseState
) {
    val exerciseStep by exerciseState.state.collectAsState()
    AlertDialog(
        onDismissRequest = {
            if (exerciseStep is ExerciseStep.ExerciseFinished) {
                onFinished()
            } else {
                exerciseState.confirmExit()
            }
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                if (exerciseStep != ExerciseStep.Initialize
                    && exerciseStep != ExerciseStep.ExerciseFinished
                    && exerciseStep !is ExerciseStep.Error) {
                    ExerciseTopBar(
                        progress = exerciseState.progress,
                        onSkip = exerciseState::next,
                        onExit = exerciseState::confirmExit,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                }
                Body(
                    exerciseStep = exerciseStep,
                    onNext = exerciseState::next,
                    onAlreadyKnow = exerciseState::markAsAlreadyKnow,
                    onDontWantLearn = exerciseState::markAsNotLearn,
                    onFinished = onFinished
                )
            }

            if (exerciseState.showExitConfirmation) {
                ExitExerciseConfirmationDialog(
                    onCancel = exerciseState::hideExitConfirmation,
                    onExit = onFinished,
                )
            }
        }
    }
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
            exerciseState = ExerciseState(
                dictionary = DictionaryMock(),
                exerciseType = ExerciseType.RANDOM
            )
        )
    }
}