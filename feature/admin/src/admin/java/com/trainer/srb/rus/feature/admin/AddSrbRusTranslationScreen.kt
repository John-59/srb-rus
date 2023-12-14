package com.trainer.srb.rus.feature.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.repository.IPredefinedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun AddSrbRusTranslationScreen(
    viewModel: AddSrbRusTranslationViewModel = hiltViewModel()
) {
    val rusWords = viewModel.rusWords
    val isBtnAddEnable by viewModel.isAddPossible

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Перевод с сербского на русский:")
        TextField(
            value = viewModel.srbLatinText,
            placeholder = {
                Text(text = "Сербский (латиница)")
            },
            onValueChange = viewModel::srbLatinChange
        )
        TextField(
            value = viewModel.srbCyrillicText,
            placeholder = {
                Text(text = "Сербский (кириллица)")
            },
            onValueChange = viewModel::srbCyrillicChange
        )
        TextField(
            value = rusWords.first(),
            placeholder = {
                Text(text = "Русский")
            },
            onValueChange = {
                viewModel.rusChange(0, it)
            }
        )

        rusWords.forEachIndexed { index, word ->
            if (index > 0) {
                TextField(
                    value = word,
                    placeholder = {
                        if (index == rusWords.lastIndex) {
                            Text("Добавить еще перевод")
                        } else {
                            Text(text = "Другой перевод")
                        }
                    },
                    onValueChange = {
                        if (index == rusWords.lastIndex) {
                            viewModel.addAnotherRussianTranslation(it)
                        } else {
                            viewModel.rusChange(index, it)
                        }
                    }
                )
            }
        }

        Button(
            enabled = isBtnAddEnable,
            onClick = viewModel::add
        ) {
            Text(text = "Добавить в словарь")
        }
    }
}

@Composable
@Preview(apiLevel = 33)
private fun AddSrbRusTranslationScreenPreview() {
    AddSrbRusTranslationScreen(
        viewModel = AddSrbRusTranslationViewModel(
            predefinedRepo = object: IPredefinedRepository {
                override val srbToRusTranslations: Flow<List<Translation<Word.Serbian, Word.Russian>>>
                    get() = emptyFlow()

                override fun addSrbToRusTranslation(srbToRusTranslation: Translation<Word.Serbian, Word.Russian>) {
                }

                override fun removeSrbToRusTranslation(srbToRusTranslation: Translation<Word.Serbian, Word.Russian>) {
                }
            }
        )
    )
}