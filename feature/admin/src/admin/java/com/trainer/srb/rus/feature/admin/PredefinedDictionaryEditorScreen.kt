package com.trainer.srb.rus.feature.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun PredefinedDictionaryEditorScreen(
    viewModel: PredefinedDictionaryEditorViewModel = hiltViewModel()
) {
    val words by viewModel.words.collectAsState()
    LazyColumn() {
        items(
            items = words,
            key = {
                it.id
            }
        ) { translation ->
            WordItem(
                translation = translation,
                onDeleteClick = {
                    viewModel.deleteTranslation(translation)
                }
            )
        }
    }
}

@Composable
private fun WordItem(
    translation: Translation<Word.Serbian, Word.Russian>,
    onDeleteClick: () -> Unit
) {
    Row(

    ) {
        TranslationItem(
            modifier = Modifier.weight(2.0f),
            translation = translation
        )
        ActionsItem(
            onDeleteClick = onDeleteClick
        )
    }
    Divider(
        modifier = Modifier.padding(top = 10.dp)
    )
}

@Composable
private fun TranslationItem(
    translation: Translation<Word.Serbian, Word.Russian>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = translation.source.value)
            if (translation.source.cyrillicValue.isNotBlank()) {
                Text(
                    modifier = Modifier.padding(start = 5.dp),
                    text = "(${translation.source.cyrillicValue})"
                )
            }
        }
        translation.translations.forEach { rusWord ->
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = rusWord.value
            )
        }
    }
}

@Composable
private fun ActionsItem(
    onDeleteClick: () -> Unit
) {
    Column(
    ) {

        Button(
            onClick = {}
        ) {
            Text("Редактировать")
        }
        Button(
            onClick = onDeleteClick
        ) {
            Text("Удалить")
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun PredefinedDictionaryEditorScreenPreview() {
    PredefinedDictionaryEditorScreen(
        viewModel = PredefinedDictionaryEditorViewModel(
            predefinedRepository = object : IPredefinedRepository {
                override val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>> = flow {
                    val demoList = listOf(
                        Translation(
                            id = 1,
                            source = Word.Serbian(
                                latinValue = "već",
                                cyrillicValue = "већ"
                            ),
                            translations = listOf(
                                Word.Russian("а"),
                                Word.Russian("уже")
                            )
                        ),
                        Translation(
                            id = 2,
                            source = Word.Serbian(
                                latinValue = "šargarepa",
                                cyrillicValue = "шаргарепа"
                            ),
                            translations = listOf(
                                Word.Russian("морковь")
                            )
                        ),
                        Translation(
                            id = 3,
                            source = Word.Serbian(
                                latinValue = "someWord",
                                cyrillicValue = ""
                            ),
                            translations = listOf(
                                Word.Russian("перевод 1"),
                                Word.Russian("перевод 2"),
                                Word.Russian("перевод 3"),
                                Word.Russian("перевод 4"),
                                Word.Russian("перевод 5")
                            )
                        ),
                        Translation(
                            id = 4,
                            source = Word.Serbian(
                                latinValue = "kiša",
                                cyrillicValue = ""
                            ),
                            translations = listOf(
                                Word.Russian("дождь")
                            )
                        )
                    )
                    emit(demoList)
                }

                override fun addSrbToRusTranslation(srbToRusTranslation: Translation<Word.Serbian, Word.Russian>) {
                }

                override fun removeSrbToRusTranslation(srbToRusTranslation: Translation<Word.Serbian, Word.Russian>) {
                }

            }
        )
    )
}