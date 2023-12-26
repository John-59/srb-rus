package com.trainer.srb.rus.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SearchScreen(
    navigateToAddWord: (Word) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val visibleWords by viewModel.visibleWords.collectAsState()
    val searchingWord by viewModel.searchingWord.collectAsState()
    val focusRequester = remember{ FocusRequester() }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndAdd(
            modifier = Modifier
                .padding(20.dp)
                .focusRequester(focusRequester),
            value = searchingWord,
            onValueChange = viewModel::searchingWordChange,
            onAddClicked = navigateToAddWord
        )
        SearchResult(
            innerWords = visibleWords,
            onRemoveTranslation = viewModel::removeTranslation,
            modifier = Modifier
                .padding(horizontal = 20.dp)

        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(apiLevel = 33)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        viewModel = SearchViewModel(
            innerDictionary = object : IDictionary {
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
            },
            remoteDictionary = object : IRemoteDictionary {
                override fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
                    return emptyList()
                }
            }
        ),
        navigateToAddWord = {}
    )
}