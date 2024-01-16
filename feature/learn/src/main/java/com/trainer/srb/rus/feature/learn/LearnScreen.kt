package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.ui.ExitExerciseConfirmationDialog
import com.trainer.srb.rus.mocks.DictionaryMock

@Composable
fun LearnScreen(
    onFinished: () -> Unit,
    viewModel: LearnViewModel = hiltViewModel()
) {
    val learnState by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.padding(20.dp)
    ) {
        if (learnState != LearnState.Initialize
            && learnState != LearnState.ExerciseFinished
            && learnState !is LearnState.Error) {
            LearnTopBar(
                progress = viewModel.progress,
                onSkip = viewModel::next,
                onExit = viewModel::showExitConfirmation,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
        Body(
            state = learnState,
            onNext = viewModel::next,
            onAlreadyKnow = viewModel::markAsAlreadyKnow,
            onDontWantLearn = viewModel::markAsNotLearn,
            onFinished = onFinished,
        )
    }

    if (viewModel.showExitConfirmation) {
        ExitExerciseConfirmationDialog(
            onCancel = viewModel::hideExitConfirmation,
            onExit = onFinished,
        )
    }
}

@Composable
private fun Body(
    state: LearnState,
    onNext: () -> Unit,
    onAlreadyKnow: (translation: Translation<Word.Serbian, Word.Russian>) -> Unit,
    onDontWantLearn: (translation: Translation<Word.Serbian, Word.Russian>) -> Unit,
    onFinished: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (state) {
        LearnState.Initialize -> {
            InitializeBody(modifier = modifier)
        }

        is LearnState.ShowInRussianAndConstructFromPredefinedLetters -> {
            ShowInRussianAndConstructFromPredefinedLettersBody(
                translation = state.translation,
                onNext = onNext,
                modifier = modifier
            )
        }

        is LearnState.ShowInRussianAndSelectSerbianVariants -> {
            ShowInRussianAndSelectSerbianVariantsBody(
                state = state,
                onNext = onNext,
                modifier = modifier
            )
        }

        is LearnState.ShowInSerbianAndSelectRussianVariants -> {
            ShowInSerbianAndSelectRussianVariantsBody(
                state = state,
                onNext = onNext,
                modifier = modifier
            )
        }

        is LearnState.ShowInRussianAndWriteInSerbian -> {
            ShowInRussianAndWriteInSerbianBody(
                state = state,
                onNext = onNext,
                modifier = modifier
            )
        }

        is LearnState.ShowInSerbianWithTranslation -> {
            ShowInSerbianWithTranslationBody(
                translation = state.translation,
                onNext = onNext,
                onAlreadyKnow = {
                    onAlreadyKnow(state.translation)
                    onNext()
                },
                onDontWantLearn = {
                    onDontWantLearn(state.translation)
                    onNext()
                },
                modifier = modifier
            )
        }

        is LearnState.Error -> {
            ErrorBody(
                message = state.message,
                modifier = modifier.fillMaxSize()
            )
        }

        LearnState.ExerciseFinished -> {
            ExerciseFinishedBody(
                onNext = onFinished,
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun LearnScreenPreview() {
    LearnScreen(
        onFinished = {},
        viewModel = LearnViewModel(
            dictionary = DictionaryMock()
        )
    )
}