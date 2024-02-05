package com.trainer.srb.rus.feature.editword

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.ui.CustomTextField
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
            modifier = Modifier.padding(bottom = 10.dp)
        )

        RussiansWords(
            russianWords = state.rusWords,
            onValueChange = state::rusChange,
            onAddRusWord = state::addRusWord,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        Button(
            onClick = {
                state.edit()
                onBack()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.Buttons,
                contentColor = MainTheme.colors.White,
            ),
            enabled = state.isTranslationValid
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.ok_checkbox),
                contentDescription = null
            )
            Text(
                text = "Сохранить",
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
            .background(
                color = MainTheme.colors.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                shape = RoundedCornerShape(10.dp)
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier.padding(5.dp)
        )
        CustomTextField(
            modifier = Modifier
                .padding(end = 5.dp)
                .fillMaxWidth()
            ,
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

@Preview(apiLevel = 33)
@Composable
private fun EditWordSuccessBodyPreview() {
    val coroutineScope = MainScope()
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