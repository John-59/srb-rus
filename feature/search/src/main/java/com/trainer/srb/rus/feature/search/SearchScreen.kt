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
import com.trainer.srb.rus.core.dictionary.IInnerDictionary
import com.trainer.srb.rus.core.dictionary.IRemoteDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val innerWords by viewModel.innerWords.collectAsState()
    val focusRequester = remember{ FocusRequester() }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchAndAdd(
            modifier = Modifier
                .padding(20.dp)
                .focusRequester(focusRequester),
            value = viewModel.searchingWord,
            onValueChange = viewModel::searchingWordChange,
        )
        SearchResult(
            innerWords = innerWords,
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
            innerDictionary = object : IInnerDictionary {
                override suspend fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
                    return emptyList()
                }

                override suspend fun getAllByAlphabet(): List<Translation<Word.Serbian, Word.Russian>> {
                    return emptyList()
                }

            },
            remoteDictionary = object : IRemoteDictionary {
                override fun search(value: String): List<Translation<Word.Serbian, Word.Russian>> {
                    return emptyList()
                }

            }
        )
    )
}