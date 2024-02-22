package com.trainer.srb.rus.feature.dictionary

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word

@Composable
fun DictionaryScreen(
    navigateToAddWord: (Word) -> Unit,
    navigateToEditWord: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DictionaryViewModel = hiltViewModel(),
) {
    val visibleWords by viewModel.visibleWords.collectAsState()
    val searchingWord by viewModel.searchingWord.collectAsState()

    DictionaryBody(
        visibleWords = visibleWords,
        searchingWord = searchingWord,
        navigateToAddWord = navigateToAddWord,
        navigateToEditWord = navigateToEditWord,
        searchingWordChange = viewModel::searchingWordChange,
        resetSearch = viewModel::resetSearch,
        removeTranslation = viewModel::removeTranslation,
        addToLearn = viewModel::addToLearn,
        setSelectionToEnd = viewModel::setSelectionToEnd,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun DictionaryBody(
    visibleWords: List<Translation<Word.Serbian, Word.Russian>>,
    searchingWord: TextFieldValue,
    navigateToAddWord: (Word) -> Unit,
    navigateToEditWord: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    searchingWordChange: (TextFieldValue) -> Unit,
    resetSearch: () -> Unit,
    removeTranslation: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    addToLearn: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    setSelectionToEnd: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember{ FocusRequester() }
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchAndAdd(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = searchingWord,
                onValueChange = searchingWordChange,
                onAddClicked = navigateToAddWord,
                onResetSearch = resetSearch
            )
            SearchResult(
                innerWords = visibleWords,
                onRemoveTranslation = removeTranslation,
                onAddToLearn = addToLearn,
                onEdit = navigateToEditWord,
                modifier = Modifier
                    .padding(horizontal = 20.dp)

            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        setSelectionToEnd()
    }
}

@Preview(apiLevel = 33)
@Composable
private fun SearchBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        DictionaryBody(
            visibleWords = translationsExample,
            searchingWord = TextFieldValue(""),
            navigateToAddWord = {},
            navigateToEditWord = {},
            searchingWordChange = {},
            resetSearch = {},
            removeTranslation = {},
            addToLearn = {},
            setSelectionToEnd = {}
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        DictionaryBody(
            visibleWords = translationsExample,
            searchingWord = TextFieldValue(""),
            navigateToAddWord = {},
            navigateToEditWord = {},
            searchingWordChange = {},
            resetSearch = {},
            removeTranslation = {},
            addToLearn = {},
            setSelectionToEnd = {}
        )
    }
}