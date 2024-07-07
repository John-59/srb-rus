package com.trainer.srb.rus.feature.dictionary

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.Colors
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word

@Composable
fun InternetSearchResult(
    translations: List<Translation<Word.Serbian, Word.Russian>>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        translations.forEach {
            InternetSearchItem(
                translation = it,
                modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
            )
        }
    }
}

@Composable
private fun InternetSearchItem(
    translation: Translation<Word.Serbian, Word.Russian>,
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

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = OutlinedTextFieldDefaults.shape
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = SrIcons.Web,
                contentDescription = null,
                tint = when (translation.type) {
                    TranslationSourceType.YANDEX -> {
                        Colors.Red
                    }
                    TranslationSourceType.GOOGLE -> {
                        Colors.PrimaryBlue
                    }
                    else -> {
                        LocalContentColor.current
                    }
                }
            )
            when (translation.type) {
                TranslationSourceType.YANDEX -> {
                    Text(
                        text = "Yandex",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.Red
                    )
                }
                TranslationSourceType.GOOGLE -> {
                    Text(
                        text = "Google",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.PrimaryBlue
                    )
                }
                else -> {}
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = serbian,
                style = MaterialTheme.typography.displayMedium
            )
            Text(
                text = russian,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun YandexSearchItemPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            InternetSearchItem(
                modifier = Modifier.fillMaxWidth(),
                translation = Translation(
                    source = Word.Serbian(
                        latinValue = "šargarepa",
                        cyrillicValue = "шаргарепа",
                        latinId = 2
                    ),
                    translations = listOf(
                        Word.Russian(value = "морковь")
                    ),
                    type = TranslationSourceType.YANDEX,
                    learningStatus = LearningStatus.Unknown()
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GoogleSearchItemPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            InternetSearchItem(
                modifier = Modifier.fillMaxWidth(),
                translation = Translation(
                    source = Word.Serbian(
                        latinValue = "šargarepa",
                        cyrillicValue = "шаргарепа",
                        latinId = 2
                    ),
                    translations = listOf(
                        Word.Russian(value = "морковь")
                    ),
                    type = TranslationSourceType.GOOGLE,
                    learningStatus = LearningStatus.Unknown()
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun InternetSearchResultPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            InternetSearchResult(
                translations = translationsExample,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}