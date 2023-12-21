package com.trainer.srb.rus.feature.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word

@Composable
fun SearchResult(
    innerWords: List<Translation<Word.Serbian, Word.Russian>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = innerWords,
            key = {
                it.uuid
            }
        ) {
            InnerSearchItem(
                translation = it,
                modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun SearchResultPreview() {
    SearchResult(
        innerWords = listOf(
            Translation(
                source = Word.Serbian(
                    latinValue = "već",
                    cyrillicValue = "већ"
                ),
                translations = listOf(
                    Word.Russian(value = "а"),
                    Word.Russian(value = "уже")
                )
            ),
            Translation(
                source = Word.Serbian(
                    latinValue = "šargarepa",
                    cyrillicValue = "шаргарепа"
                ),
                translations = listOf(
                    Word.Russian(value = "морковь")
                )
            ),
            Translation(
                source = Word.Serbian(
                    latinValue = "someWord",
                    cyrillicValue = ""
                ),
                translations = listOf(
                    Word.Russian(value = "перевод 1"),
                    Word.Russian(value = "перевод 2"),
                    Word.Russian(value = "перевод 3"),
                    Word.Russian(value = "перевод 4"),
                    Word.Russian(value = "перевод 5")
                )
            ),
            Translation(
                source = Word.Serbian(
                    latinValue = "kiša",
                    cyrillicValue = ""
                ),
                translations = listOf(
                    Word.Russian(value = "дождь")
                )
            )
        )
    )
}