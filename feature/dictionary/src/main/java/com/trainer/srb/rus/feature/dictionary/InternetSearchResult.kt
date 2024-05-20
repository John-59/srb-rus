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
import androidx.compose.material3.LinearProgressIndicator
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
import com.trainer.srb.rus.core.dictionary.RemoteTranslatorType
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word

@Composable
fun InternetSearchResult(
    yandexSearchState: InternetSearchState,
    googleSearchState: InternetSearchState,
    modifier: Modifier = Modifier
) {
    if (yandexSearchState is InternetSearchState.Disabled && googleSearchState is InternetSearchState.Disabled) {
        return
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        when (yandexSearchState) {
            InternetSearchState.Disabled -> {}
            is InternetSearchState.Error -> {
                ErrorItem(
                    RemoteTranslatorType.YANDEX,
                    yandexSearchState.message,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                )
            }
            is InternetSearchState.Loaded -> {
                yandexSearchState.translations.forEach {
                    InternetSearchItem(
                        translatorType = RemoteTranslatorType.YANDEX,
                        translation = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                }
            }
            InternetSearchState.Loading -> {
                LoadingItem(
                    RemoteTranslatorType.YANDEX,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                )
            }
        }
        when (googleSearchState) {
            InternetSearchState.Disabled -> {}
            is InternetSearchState.Error -> {
                ErrorItem(
                    RemoteTranslatorType.GOOGLE,
                    googleSearchState.message,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                )
            }
            is InternetSearchState.Loaded -> {
                googleSearchState.translations.forEach {
                    InternetSearchItem(
                        translatorType = RemoteTranslatorType.GOOGLE,
                        translation = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    )
                }
            }
            InternetSearchState.Loading -> {
                LoadingItem(
                    RemoteTranslatorType.GOOGLE,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp)
                )
            }
        }
    }
}

@Composable
private fun ErrorItem(
    translatorType: RemoteTranslatorType,
    errorMessage: String,
    modifier: Modifier = Modifier
) {
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
                tint = when (translatorType) {
                    RemoteTranslatorType.YANDEX -> {
                        Colors.Red
                    }
                    RemoteTranslatorType.GOOGLE -> {
                        Colors.PrimaryBlue
                    }
                }
            )
            when (translatorType) {
                RemoteTranslatorType.YANDEX -> {
                    Text(
                        text = "Yandex",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.Red
                    )
                }
                RemoteTranslatorType.GOOGLE -> {
                    Text(
                        text = "Google",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.PrimaryBlue
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Composable
private fun LoadingItem(
    translatorType: RemoteTranslatorType,
    modifier: Modifier = Modifier
) {
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
                tint = when (translatorType) {
                    RemoteTranslatorType.YANDEX -> {
                        Colors.Red
                    }
                    RemoteTranslatorType.GOOGLE -> {
                        Colors.PrimaryBlue
                    }
                }
            )
            when (translatorType) {
                RemoteTranslatorType.YANDEX -> {
                    Text(
                        text = "Yandex",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.Red
                    )
                }
                RemoteTranslatorType.GOOGLE -> {
                    Text(
                        text = "Google",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.PrimaryBlue
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun InternetSearchItem(
    translatorType: RemoteTranslatorType,
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
                tint = when (translatorType) {
                    RemoteTranslatorType.YANDEX -> {
                        Colors.Red
                    }
                    RemoteTranslatorType.GOOGLE -> {
                        Colors.PrimaryBlue
                    }
                }
            )
            when (translatorType) {
                RemoteTranslatorType.YANDEX -> {
                    Text(
                        text = "Yandex",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.Red
                    )
                }
                RemoteTranslatorType.GOOGLE -> {
                    Text(
                        text = "Google",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = Colors.PrimaryBlue
                    )
                }
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
                translatorType = RemoteTranslatorType.YANDEX,
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
                    type = TranslationSourceType.INTERNET,
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
                translatorType = RemoteTranslatorType.GOOGLE,
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
                    type = TranslationSourceType.INTERNET,
                    learningStatus = LearningStatus.Unknown()
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun YandexErrorItemPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            ErrorItem(
                translatorType = RemoteTranslatorType.YANDEX,
                errorMessage = "Что-то пошло не так.",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GoogleErrorItemPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            ErrorItem(
                translatorType = RemoteTranslatorType.GOOGLE,
                errorMessage = "Что-то пошло не так.",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun YandexLoadingItemPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            LoadingItem(
                translatorType = RemoteTranslatorType.YANDEX,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GoogleLoadingItemPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            LoadingItem(
                translatorType = RemoteTranslatorType.GOOGLE,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@PreviewLightDark
@Composable
private fun InternetSearchResultPreview() {
    val yandexSearchState = InternetSearchState.Loaded(
        translationsExample.take(2)
    )
    val googleSearchState = InternetSearchState.Loaded(
        translationsExample.takeLast(2)
    )
    MainTheme(dynamicColor = false) {
        Surface {
            InternetSearchResult(
                yandexSearchState = yandexSearchState,
                googleSearchState = googleSearchState,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun InternetSearchResultYandexErrorPreview() {
    val yandexSearchState = InternetSearchState.Error(
        "Что-то пошло не так и дальше, например, какой-то очень длинный текст ошибки. Настолько длинный, что не помещается в одну строку."
    )
    val googleSearchState = InternetSearchState.Loaded(
        translationsExample.takeLast(2)
    )
    MainTheme(dynamicColor = false) {
        Surface {
            InternetSearchResult(
                yandexSearchState = yandexSearchState,
                googleSearchState = googleSearchState,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun InternetSearchResultGoogleErrorPreview() {
    val googleSearchState = InternetSearchState.Error(
        "Что-то пошло не так и дальше, например, какой-то очень длинный текст ошибки. Настолько длинный, что не помещается в одну строку."
    )
    val yandexSearchState = InternetSearchState.Loaded(
        translationsExample.takeLast(2)
    )
    MainTheme(dynamicColor = false) {
        Surface {
            InternetSearchResult(
                yandexSearchState = yandexSearchState,
                googleSearchState = googleSearchState,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}