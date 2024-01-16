package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.R as DesignRes
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.dictionary.russianAsString
import com.trainer.srb.rus.core.dictionary.serbianAsString

@Composable
fun ShowInSerbianWithTranslationBody(
    translation: Translation<Word.Serbian, Word.Russian>,
    onNext: () -> Unit,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Word(
            translation = translation,
            modifier = Modifier
                .weight(2.0f)
                .fillMaxWidth()
        )
        Actions(
            onNext = onNext,
            onAlreadyKnow = onAlreadyKnow,
            onDontWantLearn = onDontWantLearn,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Actions(
    onNext: () -> Unit,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onAlreadyKnow,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MainTheme.colors.Border),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.White,
            )
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.ok_checkbox_green),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Уже знаю это слово",
                style = MainTheme.typography.displayMedium.copy(
                    baselineShift = BaselineShift(-0.2f)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            onClick = onDontWantLearn,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, MainTheme.colors.Border),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.White,
            )
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.cross_red),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Не хочу учить это слово",
                style = MainTheme.typography.displayMedium.copy(
                    baselineShift = BaselineShift(-0.2f)
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
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
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        onNext = {},
        onAlreadyKnow = {},
        onDontWantLearn = {},
        translation = Translation(
            source = Word.Serbian(
                latinValue = "kašika",
                cyrillicValue = "кашика"
            ),
            translations = listOf(
                Word.Russian(value = "ложка")
            ),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.UNKNOWN
        )
    )
}