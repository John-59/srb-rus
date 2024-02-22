package com.trainer.srb.rus.feature.addword

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun AddWordScreen(
    viewModel: AddWordViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            SerbianWord(
                serbianWord = viewModel.srbWord,
                onSrbLatChange = viewModel::srbLatinChange,
                onSrbCyrChange = viewModel::srbCyrillicChange,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            RussiansWords(
                russianWords = viewModel.rusWords,
                onValueChange = viewModel::rusChange,
                onAddRusWord = viewModel::addRusWord,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {
                    viewModel.add()
                    onBack()
                },
                enabled = viewModel.srbWord.latinValue.isNotBlank()
            ) {
                Icon(
                    imageVector = SrIcons.AddCirce,
                    contentDescription = null
                )
                Text(
                    text = "Добавить в словарь",
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RussiansWords(
    russianWords: List<Word.Russian>,
    onValueChange: (index: Int, value: String) -> Unit,
    onAddRusWord: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        russianWords.forEachIndexed { index, word ->
            WordItem(
                value = word.value,
                onValueChange = {
                    if (index == russianWords.lastIndex) {
                        onAddRusWord(it)
                    } else {
                        onValueChange(index, it)
                    }
                },
                iconId = DesignRes.drawable.rus,
                placeholder = if (index == russianWords.lastIndex) {
                    "Добавить синоним"
                } else {
                    "Русский"
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}

@Composable
private fun SerbianWord(
    serbianWord: Word.Serbian,
    onSrbLatChange: (String) -> Unit,
    onSrbCyrChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        WordItem(
            value = serbianWord.latinValue,
            onValueChange = onSrbLatChange,
            placeholder = "Сербский (латиница)",
            iconId = DesignRes.drawable.srblat,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        WordItem(
            value = serbianWord.cyrillicValue,
            onValueChange = onSrbCyrChange,
            placeholder = "Сербский (кириллица)",
            iconId = DesignRes.drawable.srbcyr,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WordItem(
    value: String,
    onValueChange: (String) -> Unit,
    @DrawableRes iconId: Int,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Image(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.padding(5.dp)
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.outline,
            )
        },
        textStyle = MaterialTheme.typography.displayMedium,
        modifier = modifier
    )
}

@Composable
@Preview(apiLevel = 33)
private fun AddWordScreenPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        AddWordScreen(
            viewModel = AddWordViewModel(
                savedStateHandle = SavedStateHandle(),
                dictionary = DictionaryMock()
            ),
            onBack = {}
        )
    }
}

@Composable
@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun AddWordScreenNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        AddWordScreen(
            viewModel = AddWordViewModel(
                savedStateHandle = SavedStateHandle(),
                dictionary = DictionaryMock()
            ),
            onBack = {}
        )
    }
}