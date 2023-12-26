package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.dictionary.russianAsString
import com.trainer.srb.rus.core.dictionary.serbianAsString

@Composable
fun ShowInSerbianWithTranslationBody(
    translation: Translation<Word.Serbian, Word.Russian>,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Word(
            translation = translation,
            modifier = Modifier.weight(2.0f).fillMaxWidth()
        )
        Actions(
            onNext = onNext,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Actions(
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Button(onClick = {}) {
//            Text(
//                text = "Уже знаю это слово"
//            )
//        }
//        Button(onClick = {}) {
//            Text(
//                text = "Не хочу учить это слово"
//            )
//        }
//        Button(onClick = {}) {
//            Text(
//                text = "Учить заново"
//            )
//        }
        Button(
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.Buttons,
                contentColor = MainTheme.colors.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext
        ) {
            Text(
                text = "Далее",
                style = MainTheme.typography.displayMedium
            )
        }
    }
}

@Composable
private fun Word(
    translation: Translation<Word.Serbian, Word.Russian>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                text = translation.serbianAsString(),
                style = MainTheme.typography.titleMedium
            )
            Text(
                text = translation.russianAsString(),
                style = MainTheme.typography.titleSmall
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ShowInSerbianWithTranslationBodyPreview() {
    ShowInSerbianWithTranslationBody(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        onNext = {},
        translation = Translation(
            source = Word.Serbian(
                latinValue = "kašika",
                cyrillicValue = "кашика"
            ),
            translations = listOf(
                Word.Russian(value = "ложка")
            )
        )
    )
}