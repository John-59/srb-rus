package com.trainer.srb.rus.feature.dictionary

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.getProgress
import com.trainer.srb.rus.core.translation.serbianAsString

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
                         learningProgress = translation.getProgress(),
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
                            .background(
                                color = MaterialTheme.colorScheme.surface
                            )
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
    learningProgress: Float,
    direction: DismissDirection?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = if (direction == DismissDirection.EndToStart) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.inverseSurface
                },
                shape = RoundedCornerShape(1.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (direction == DismissDirection.EndToStart) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        if (direction == DismissDirection.EndToStart) {
            Text(
                text = "Удалить\nиз словаря",
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Right,
                color = MaterialTheme.colorScheme.onError
            )
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = SrIcons.DeleteWord,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onError
            )
            Spacer(modifier = Modifier.width(5.dp))
        } else if (learningProgress == 0f) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = SrIcons.LearnBorder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Добавить в\nизучаемые слова",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
        } else {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                imageVector = SrIcons.Repeat,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.inverseOnSurface
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Учить\nзаново",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.inverseOnSurface
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun ItemSwipeDeleteBackgroundPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ItemSwipeBackground(
            learningProgress = 0f,
            direction = DismissDirection.EndToStart,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemSwipeDeleteBackgroundNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        Surface {
            ItemSwipeBackground(
                learningProgress = 0f,
                direction = DismissDirection.EndToStart,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun ItemSwipeAddToLearnedBackgroundPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ItemSwipeBackground(
            learningProgress = 0f,
            direction = DismissDirection.StartToEnd,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemSwipeAddToLearnedBackgroundNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        Surface {
            ItemSwipeBackground(
                learningProgress = 0f,
                direction = DismissDirection.StartToEnd,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun ItemSwipeLearnAgainBackgroundPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ItemSwipeBackground(
            learningProgress = 0.3f,
            direction = DismissDirection.StartToEnd,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ItemSwipeLearnAgainBackgroundNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        Surface {
            ItemSwipeBackground(
                learningProgress = 0.3f,
                direction = DismissDirection.StartToEnd,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun SearchResultPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        SearchResult(
            onRemoveTranslation = {},
            onAddToLearn = {},
            onEdit = {},
            innerWords = translationsExample
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchResultNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        Surface {
            SearchResult(
                onRemoveTranslation = {},
                onAddToLearn = {},
                onEdit = {},
                innerWords = translationsExample
            )
        }
    }
}