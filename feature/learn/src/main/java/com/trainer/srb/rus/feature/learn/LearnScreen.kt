package com.trainer.srb.rus.feature.learn

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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LearnScreen(
     viewModel: LearnViewModel = hiltViewModel()
) {
    val learnState by viewModel.state.collectAsState()
    Body(
        state = learnState,
        onNext = viewModel::next
    )
}

@Composable
private fun Body(
    state: LearnState,
    onNext: () -> Unit
) {
    when (state) {
        LearnState.Initialize -> {
            InitializeBody()
        }

        is LearnState.ShowInRussianAndConstructFromPredefinedLetters -> {
            ShowInRussianAndConstructFromPredefinedLettersBody()
        }

        is LearnState.ShowInRussianAndSelectSerbianVariants -> {
            ShowInRussianAndSelectSerbianVariantsBody()
        }

        is LearnState.ShowInRussianAndWriteInSerbian -> {
            ShowInRussianAndWriteInSerbianBody()
        }

        is LearnState.ShowInSerbianWithTranslation -> {
            ShowInSerbianWithTranslationBody(
                translation = state.translation,
                onNext = onNext,
                modifier = Modifier.padding(20.dp)
            )
        }

        is LearnState.Error -> {
            ErrorBody(
                message = state.message,
                modifier = Modifier.fillMaxSize().padding(20.dp)
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun LearnScreenPreview() {
    LearnScreen(
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