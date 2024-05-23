package com.trainer.srb.rus.feature.dictionary

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
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
    val yandexSearchState by viewModel.yandexSearchState.collectAsState()
    val googleSearchState by viewModel.googleSearchState.collectAsState()

    DictionaryBody(
        yandexSearchState = yandexSearchState,
        googleSearchState = googleSearchState,
        visibleWords = visibleWords,
        searchingWord = searchingWord,
        navigateToAddWord = navigateToAddWord,
        navigateToEditWord = navigateToEditWord,
        searchingWordChange = viewModel::searchingWordChange,
        resetSearch = viewModel::resetSearch,
        removeTranslation = viewModel::removeTranslation,
        addToLearn = viewModel::addToLearn,
        setSelectionToEnd = viewModel::setSelectionToEnd,
        internetSearchRusToSrb = viewModel::internetSearchRusToSrb,
        internetSearchSrbToRus = viewModel::internetSearchSrbToRus,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
private fun DictionaryBody(
    yandexSearchState: InternetSearchState,
    googleSearchState: InternetSearchState,
    visibleWords: List<Translation<Word.Serbian, Word.Russian>>,
    searchingWord: TextFieldValue,
    navigateToAddWord: (Word) -> Unit,
    navigateToEditWord: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    searchingWordChange: (TextFieldValue) -> Unit,
    resetSearch: () -> Unit,
    removeTranslation: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    addToLearn: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    setSelectionToEnd: () -> Unit,
    internetSearchRusToSrb: (String) -> Unit,
    internetSearchSrbToRus: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember{ FocusRequester() }
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        if (searchingWord.text.isBlank()) {
                            Toast.makeText(
                                context,
                                "Чтобы поискать перевод слова или фразы в интернете, сначала наберите их в строке поиска, а потом нажмите эту кнопку.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            internetSearchSrbToRus(searchingWord.text)
                        }
                    },

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(imageVector = SrIcons.Web, contentDescription = null)
                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            Text(text = "srb")
                            Text("rus")
                        }
                    }

                }
                Spacer(modifier = Modifier.width(10.dp))
                FloatingActionButton(
                    onClick = {
                        if (searchingWord.text.isBlank()) {
                            Toast.makeText(
                                context,
                                "Чтобы поискать перевод слова или фразы в интернете, сначала наберите их в строке поиска, а потом нажмите эту кнопку.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            internetSearchRusToSrb(searchingWord.text)
                        }
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(imageVector = SrIcons.Web, contentDescription = null)
                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            Text(text = "rus")
                            Text("srb")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
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
            InternetSearchResult(
                yandexSearchState = yandexSearchState,
                googleSearchState = googleSearchState,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
            )
            SearchResult(
                innerWords = visibleWords,
                onRemoveTranslation = removeTranslation,
                onAddToLearn = addToLearn,
                onEdit = navigateToEditWord,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
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
            yandexSearchState = InternetSearchState.Disabled,
            googleSearchState = InternetSearchState.Disabled,
            visibleWords = translationsExample,
            searchingWord = TextFieldValue(""),
            navigateToAddWord = {},
            navigateToEditWord = {},
            searchingWordChange = {},
            resetSearch = {},
            removeTranslation = {},
            addToLearn = {},
            setSelectionToEnd = {},
            internetSearchRusToSrb = {},
            internetSearchSrbToRus = {}
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
            yandexSearchState = InternetSearchState.Disabled,
            googleSearchState = InternetSearchState.Disabled,
            visibleWords = translationsExample,
            searchingWord = TextFieldValue(""),
            navigateToAddWord = {},
            navigateToEditWord = {},
            searchingWordChange = {},
            resetSearch = {},
            removeTranslation = {},
            addToLearn = {},
            setSelectionToEnd = {},
            internetSearchRusToSrb = {},
            internetSearchSrbToRus = {}
        )
    }
}