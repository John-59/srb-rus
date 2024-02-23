package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word

@Composable
fun ShowInRussianAndConstructFromPredefinedLettersBody(
    translation: Translation<Word.Serbian, Word.Russian>,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Слово на русском и написать из предложенных букв",
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
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = MainTheme.colors.Buttons,
//                contentColor = MainTheme.colors.White,
//            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext
        ) {
            Text(
                text = "Далее",
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ShowInRussianAndConstructFromPredefinedLettersBodyPreview() {
    ShowInRussianAndConstructFromPredefinedLettersBody(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        onNext = {},
        translation = Translation(
            source = Word.Serbian(
                latinValue = "kašika",
                cyrillicValue = "кашика"
            ),
            translations = listOf(
                Word.Russian(value = "ложка")
            ),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.Unknown()
        )
    )
}