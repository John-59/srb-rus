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
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.ui.ExitExerciseConfirmationDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

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
            dictionary = object : IDictionary {
                override val translations: Flow<List<Translation<Word.Serbian, Word.Russian>>>
                    get() = emptyFlow()

                override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
                    return emptyList()
                }

                override suspend fun add(translation: Translation<Word.Serbian, Word.Russian>) {
                }

                override suspend fun remove(translation: Translation<Word.Serbian, Word.Russian>) {
                }

                override suspend fun getRandom(randomTranslationsCount: Int): List<Translation<Word.Serbian, Word.Russian>> {
                    return emptyList()
                }
            }
        )
    )
}