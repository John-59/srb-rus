package com.trainer.srb.rus.feature.editword

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import kotlinx.coroutines.MainScope
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun EditWordSuccessBody(
    state: EditWordState.Success,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SerbianWord(
            serbianWord = state.srbWord,
            onSrbLatChange = state::srbLatinChange,
            onSrbCyrChange = state::srbCyrillicChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        RussiansWords(
            russianWords = state.rusWords,
            onValueChange = state::rusChange,
            onAddRusWord = state::addRusWord,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(15.dp))

        Button(
            onClick = {
                state.edit()
                onBack()
            },
            enabled = state.isTranslationValid
        ) {
            Icon(imageVector = SrIcons.Save, contentDescription = null)
            Text(
                text = "Сохранить",
                style = MaterialTheme.typography.displayMedium.copy(
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

@Preview(apiLevel = 33)
@Composable
private fun EditWordSuccessBodyPreview() {
    val coroutineScope = MainScope()
    MainTheme(
        dynamicColor = false
    ) {
        EditWordSuccessBody(
            state = EditWordState.Success(
                translation = Translation(
                    source = Word.Serbian(
                        latinValue = "šargarepa",
                        cyrillicValue = "шаргарепа"
                    ),
                    translations = listOf(
                        Word.Russian(value = "морковь")
                    ),
                    type = TranslationSourceType.USER,
                    learningStatus = LearningStatus.Unknown()
                ),
                dictionary = DictionaryMock(),
                coroutineScope = coroutineScope
            ),
            onBack = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}