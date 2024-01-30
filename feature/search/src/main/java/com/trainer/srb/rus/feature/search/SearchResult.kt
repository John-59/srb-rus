package com.trainer.srb.rus.feature.search

import android.widget.Toast
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.dictionary.serbianAsString
import com.trainer.srb.rus.mocks.translationsExample
import com.trainer.srb.rus.core.design.R as DesignRes

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchResult(
    innerWords: List<Translation<Word.Serbian, Word.Russian>>,
    onRemoveTranslation: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    onAddToLearn: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    onEdit: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val dismissThreshold = 0.5f
    val currentFraction = remember { mutableFloatStateOf(0f) }
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
                if (currentFraction.floatValue >= dismissThreshold && currentFraction.floatValue < 1f ) {
                    when (it) {
                        DismissValue.DismissedToStart -> {
                            onRemoveTranslation(translation)
                            true
                        }

                        DismissValue.DismissedToEnd -> {
                            onAddToLearn(translation)
                            Toast.makeText(
                                context,
                                "Слово ${translation.serbianAsString()} выбрано для изучения.",
                                Toast.LENGTH_LONG
                            ).show()
                            false
                        }

                        else -> {
                            false
                        }
                    }
                } else {
                    false
                }
            }
            val removeDirection = DismissDirection.EndToStart
            val learnDirection = DismissDirection.StartToEnd
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(removeDirection, learnDirection),
                dismissThresholds = {
                    FractionalThreshold(dismissThreshold)
                },
                background = {
                    currentFraction.floatValue = dismissState.progress.fraction
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

@Composable
private fun ItemSwipeBackground(
    direction: DismissDirection?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = if (direction == DismissDirection.EndToStart) {
                    MainTheme.colors.Delete
                } else {
                   MainTheme.colors.Right
                },
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (direction == DismissDirection.EndToStart) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Spacer(modifier = Modifier)
        Image(
            painter = if (direction == DismissDirection.EndToStart) {
                painterResource(id = DesignRes.drawable.delete)
            } else {
                painterResource(id = DesignRes.drawable.add_to_learn)
            },
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun ItemSwipeDeleteBackgroundPreview() {
    ItemSwipeBackground(
        direction = DismissDirection.EndToStart,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(apiLevel = 33)
@Composable
private fun ItemSwipeAddToLearnedBackgroundPreview() {
    ItemSwipeBackground(
        direction = DismissDirection.StartToEnd,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(apiLevel = 33)
@Composable
private fun ItemSwipeLearnAgainBackgroundPreview() {
    ItemSwipeBackground(
        direction = DismissDirection.StartToEnd,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(apiLevel = 33)
@Composable
private fun SearchResultPreview() {
    SearchResult(
        onRemoveTranslation = {},
        onAddToLearn = {},
        onEdit = {},
        innerWords = translationsExample
    )
}