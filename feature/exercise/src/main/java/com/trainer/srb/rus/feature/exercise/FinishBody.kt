package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons
import com.trainer.srb.rus.core.exercise.ExerciseStep
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.ui.InnerSearchItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinishBody(
    state: ExerciseStepState.Finished,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Слова, которые вы учили",
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = "Смахните слово вправо, если хотите повторить его еще раз.",
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(Modifier.height(5.dp))
            state.translations.forEach { translation ->
                val dismissThreshold = 0.5f
                val currentFraction = remember { mutableFloatStateOf(0f) }
                val swipeState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (currentFraction.floatValue >= dismissThreshold && currentFraction.floatValue < 1f ) {
                            state.repeatAgain(translation)
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
                        ItemSwipeBackground(
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

@Composable
private fun ItemSwipeBackground(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.background(MaterialTheme.colorScheme.inverseSurface),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(imageVector = SrIcons.Repeat, contentDescription = "Повторить еще раз")
        Spacer(Modifier.width(5.dp))
        Text(text = "Повторить\n еще раз")
    }
}

@PreviewLightDark
@Composable
private fun ItemSwipeBackgroundPreview() {
    MainTheme(dynamicColor = false) {
        ItemSwipeBackground(
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
                    translationsExample.take(7)
                )
            )
        )
    }
}