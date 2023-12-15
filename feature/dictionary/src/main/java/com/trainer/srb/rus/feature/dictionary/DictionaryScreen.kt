package com.trainer.srb.rus.feature.dictionary

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun DictionaryScreen(
    viewModel: DictionaryViewModel = hiltViewModel()
) {
    val words by viewModel.words.collectAsState()
    LazyColumn() {
        items(
            items = words
        ) { translation ->
            Text(translation.source.value)
            Text(translation.source.cyrillicValue)
            translation.translations.forEach {
                Text(it.value)
            }
            Divider()
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun DictionaryScreenPreview() {
    DictionaryScreen(
        viewModel = DictionaryViewModel(
            predefinedRepository = object : IPredefinedRepository {
                override val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>> =
                    flow {
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