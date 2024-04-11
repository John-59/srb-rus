package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.exercise.ExerciseStep
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.ui.InnerSearchItem
import kotlinx.coroutines.MainScope

@Composable
fun FinishBody(
    state: ExerciseStepState.Finished,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val learnedWords by state.learnedWords.collectAsState()
    val repeatAgainWords by state.repeatAgainWords.collectAsState()
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (learnedWords.isNotEmpty()) {
                LearnedWords(
                    learnedWords = learnedWords,
                    repeatAgain = state::repeatAgain
                )
            }
            if (repeatAgainWords.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                RepeatAgainWords(
                    repeatAgainWords = repeatAgainWords,
                    notRepeatAgain = state::notRepeatAgain
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNext
                ) {
                    Text(
                        text = "Упражнение закончено",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LearnedWords(
    learnedWords: List<Translation<Word.Serbian, Word.Russian>>,
    repeatAgain: (Translation<Word.Serbian, Word.Russian>) -> Unit
) {
    Text(
        text = "Слова, которые вы учили:",
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(Modifier.height(5.dp))
    Text(
        text = "Смахните слово вправо, если хотите повторить его еще раз.",
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(Modifier.height(5.dp))
    LazyColumn {
        items(
            items = learnedWords,
            key = {
                it.uuid
            }
        ) { translation ->
            val dismissThreshold = 0.5f
            val currentFraction = remember { mutableFloatStateOf(0f) }
            val swipeState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (currentFraction.floatValue >= dismissThreshold && currentFraction.floatValue < 1f ) {
                        repeatAgain(translation)
                        true
                    } else {
                        false
                    }
                }
            )
            SwipeToDismissBox(
                state = swipeState,
                enableDismissFromEndToStart = false,
                backgroundContent = {
                    currentFraction.floatValue = swipeState.progress
                    RepeatAgainBackground(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxSize()
                    )
                }
            ) {
                InnerSearchItem(
                    translation = translation,
                    onEdit = {},
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        )
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepeatAgainWords(
    repeatAgainWords: List<Translation<Word.Serbian, Word.Russian>>,
    notRepeatAgain: (Translation<Word.Serbian, Word.Russian>) -> Unit
) {
    Text(
        text = "Слова, которые вы хотите повторить еще раз:",
        style = MaterialTheme.typography.titleMedium,
    )
    Spacer(Modifier.height(5.dp))
    Text(
        text = "Смахните слово влево, чтобы удалить его из этого списка.",
        style = MaterialTheme.typography.displayMedium
    )
    Spacer(Modifier.height(5.dp))
    LazyColumn {
        items(
            items = repeatAgainWords,
            key = {
                it.uuid
            }
        ) { translation ->
            val dismissThreshold = 0.5f
            val currentFraction = remember { mutableFloatStateOf(0f) }
            val swipeState = rememberSwipeToDismissBoxState(
                confirmValueChange = {
                    if (currentFraction.floatValue >= dismissThreshold && currentFraction.floatValue < 1f ) {
                        notRepeatAgain(translation)
                        true
                    } else {
                        false
                    }
                }
            )
            SwipeToDismissBox(
                state = swipeState,
                enableDismissFromStartToEnd = false,
                backgroundContent = {
                    currentFraction.floatValue = swipeState.progress
                    NotRepeatAgainBackground(
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .fillMaxSize()
                    )
                }
            ) {
                InnerSearchItem(
                    translation = translation,
                    onEdit = {},
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        )
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun RepeatAgainBackground(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(MaterialTheme.colorScheme.inverseSurface),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = SrIcons.Repeat, contentDescription = "Повторить еще раз")
        Spacer(Modifier.width(5.dp))
        Text(text = "Повторить\nеще раз")
    }
}

@Composable
private fun NotRepeatAgainBackground(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(MaterialTheme.colorScheme.error),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            textAlign = TextAlign.Right,
            text = "Убрать из\nповторений"
        )
        Spacer(Modifier.width(5.dp))
        Icon(imageVector = SrIcons.DeleteWord, contentDescription = "Повторить еще раз")
    }
}

@PreviewLightDark
@Composable
private fun RepeatAgainBackgroundPreview() {
    MainTheme(dynamicColor = false) {
        RepeatAgainBackground(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@PreviewLightDark
@Composable
private fun NotRepeatAgainBackgroundPreview() {
    MainTheme(dynamicColor = false) {
        NotRepeatAgainBackground(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@PreviewLightDark
@Composable
fun FinishBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        FinishBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            onNext = {},
            state = ExerciseStepState.Finished(
                step = ExerciseStep.Finished(
                    translations = translationsExample.take(7)
                ),
                coroutineScope = MainScope()
            ).apply {
                repeatAgain(translationsExample.first())
            }
        )
    }
}