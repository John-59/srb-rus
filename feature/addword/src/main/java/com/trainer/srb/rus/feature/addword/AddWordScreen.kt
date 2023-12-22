package com.trainer.srb.rus.feature.addword

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.IDictionary
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.ui.CustomTextField
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun AddWordScreen(
    modifier: Modifier = Modifier,
    viewModel: AddWordViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SerbianWord(
            serbianWord = viewModel.srbWord,
            onSrbLatChange = viewModel::srbLatinChange,
            onSrbCyrChange = viewModel::srbCyrillicChange,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        RussiansWords(
            russianWords = viewModel.rusWords,
            onValueChange = viewModel::rusChange,
            onAddRusWord = viewModel::addRusWord,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = {
                viewModel.add()
                onBack()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.Buttons,
                contentColor = MainTheme.colors.White,
            ),
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.plusforbtn),
                contentDescription = null
            )
            Text(
                text = "Добавить в словарь",
                style = MainTheme.typography.displayMedium.copy(
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(start = 10.dp)
            )
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
                modifier = Modifier.padding(bottom = 5.dp)
            )
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
            iconId = DesignRes.drawable.srblat
        )

        WordItem(
            value = serbianWord.cyrillicValue,
            onValueChange = onSrbCyrChange,
            placeholder = "Сербский (кириллица)",
            iconId = DesignRes.drawable.srbcyr,
            modifier = Modifier.padding(vertical = 5.dp)
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
    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MainTheme.colors.Border)
            )
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(iconId),
                contentDescription = null
            )
            CustomTextField(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(),
                textStyle = MainTheme.typography.displayMedium.copy(
                    baselineShift = BaselineShift(-0.2f)
                ),
                value = value,
                placeholder = {
                    Text(
                        text = placeholder,
                        style = MainTheme.typography.displayMedium.copy(
                            baselineShift = BaselineShift(-0.2f)
                        ),
                        color = MainTheme.colors.Tips,
                    )
                },
                onValueChange = onValueChange
            )
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun AddWordScreenPreview() {
    AddWordScreen(
        viewModel = AddWordViewModel(
            savedStateHandle = SavedStateHandle(),
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
            }
        ),
        onBack = {}
    )
}