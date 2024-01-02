package com.trainer.srb.rus.feature.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.mocks.translationsExample
import com.trainer.srb.rus.core.design.R as DesignRes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchResult(
    innerWords: List<Translation<Word.Serbian, Word.Russian>>,
    onRemoveTranslation: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    onEdit: (Translation<Word.Serbian, Word.Russian>) -> Unit,
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
        ) { translation ->
            val dismissState = rememberDismissState {
                if (it == DismissValue.DismissedToStart) {
                    onRemoveTranslation(translation)
                    true
                } else {
                    false
                }
            }
            val dismissDirection = DismissDirection.EndToStart
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(dismissDirection),
                dismissThresholds = {
                    FractionalThreshold(0.5f)
                },
                background = {
                     ItemSwipeBackground(
                         direction = dismissState.dismissDirection,
                         modifier = Modifier
                             .padding(vertical = 5.dp)
                             .fillMaxSize()
                     )
                },
                dismissContent = {
                    InnerSearchItem(
                        translation = translation,
                        onEdit = onEdit,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxWidth()
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ItemSwipeBackground(
    direction: DismissDirection?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = MainTheme.colors.Delete,
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier)
        if (direction == DismissDirection.EndToStart) {
            Image(
                painter = painterResource(id = DesignRes.drawable.delete),
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ItemSwipeBackgroundPreview() {
    ItemSwipeBackground(
        direction = DismissDirection.EndToStart,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun SearchResultPreview() {
    SearchResult(
        onRemoveTranslation = {},
        onEdit = {},
        innerWords = translationsExample
    )
}