package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.russianAsString
import com.trainer.srb.rus.core.translation.serbianAsString

@Composable
fun ShowInSerbianWithTranslationBody(
    translation: Translation<Word.Serbian, Word.Russian>,
    onNext: () -> Unit,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Word(
                translation = translation,
                modifier = Modifier
                    .weight(2.0f)
                    .fillMaxWidth()
            )
            WordActions(
                onNext = onNext,
                isNextEnabled = true,
                onAlreadyKnow = onAlreadyKnow,
                onDontWantLearn = onDontWantLearn,
                modifier = Modifier.fillMaxWidth()
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
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = translation.russianAsString(),
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ShowInSerbianWithTranslationBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ShowInSerbianWithTranslationBody(
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
                learningStatus = LearningStatus.Unknown()
            )
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ShowInSerbianWithTranslationBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ShowInSerbianWithTranslationBody(
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
                learningStatus = LearningStatus.Unknown()
            )
        )
    }
}