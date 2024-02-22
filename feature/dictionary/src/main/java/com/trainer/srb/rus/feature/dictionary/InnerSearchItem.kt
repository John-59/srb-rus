package com.trainer.srb.rus.feature.dictionary

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.getProgress

@Composable
fun InnerSearchItem(
    translation: Translation<Word.Serbian, Word.Russian>,
    onEdit: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    modifier: Modifier = Modifier
) {
    val serbian = listOf(
        translation.source.latinValue,
        translation.source.cyrillicValue
    ).filter {
        it.isNotBlank()
    }.joinToString(" / ")

    val russian = translation.translations.joinToString(", ") {
        it.value
    }

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = OutlinedTextFieldDefaults.shape
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable {
                onEdit(translation)
            }
    ) {
        Text(
            text = serbian,
            style = MaterialTheme.typography.displayMedium //MainTheme.typography.displayMedium
        )
        Text(
            text = russian,
            style = MaterialTheme.typography.displaySmall
        )
        LinearProgressIndicator(
            progress = translation.getProgress(),
            color = MaterialTheme.colorScheme.inverseSurface,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewOne() {
    MainTheme(
        dynamicColor = false
    ) {
        InnerSearchItem(
            translation = Translation(
                source = Word.Serbian(
                    latinValue = "kiša",
                    cyrillicValue = "киша"
                ),
                translations = listOf(
                    Word.Russian(value = "дождь")
                ),
                type = TranslationSourceType.USER,
                learningStatus = LearningStatus.AlreadyKnow()
            ),
            onEdit = {}
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewTwo() {
    MainTheme(
        dynamicColor = false
    ) {
        InnerSearchItem(
            translation = Translation(
                source = Word.Serbian(
                    latinValue = "već",
                    cyrillicValue = "већ"
                ),
                translations = listOf(
                    Word.Russian(value = "уже"),
                    Word.Russian(value = "а")
                ),
                type = TranslationSourceType.USER,
                learningStatus = LearningStatus.New()
            ),
            onEdit = {}
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewThree() {
    MainTheme(
        dynamicColor = false
    ) {
        InnerSearchItem(
            translation = Translation(
                source = Word.Serbian(
                    latinValue = "šargarepa",
                    cyrillicValue = ""
                ),
                translations = listOf(
                    Word.Russian(value = "морковь"),
                ),
                type = TranslationSourceType.USER,
                learningStatus = LearningStatus.NextDay()
            ),
            onEdit = {}
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewFour() {
    MainTheme(
        dynamicColor = false
    ) {
        InnerSearchItem(
            translation = Translation(
                source = Word.Serbian(
                    latinValue = "",
                    cyrillicValue = "књига"
                ),
                translations = listOf(
                    Word.Russian(value = "книга"),
                ),
                type = TranslationSourceType.USER,
                learningStatus = LearningStatus.AfterTwoWeeks()
            ),
            onEdit = {}
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InnerSearchItemPreviewFourNight() {
    MainTheme(
        dynamicColor = false
    ) {
        Surface {
            InnerSearchItem(
                translation = Translation(
                    source = Word.Serbian(
                        latinValue = "",
                        cyrillicValue = "књига"
                    ),
                    translations = listOf(
                        Word.Russian(value = "книга"),
                    ),
                    type = TranslationSourceType.USER,
                    learningStatus = LearningStatus.AfterTwoWeeks()
                ),
                onEdit = {}
            )
        }
    }
}