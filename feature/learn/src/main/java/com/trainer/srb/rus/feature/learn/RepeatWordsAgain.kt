package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.SrIcons

@Composable
fun RepeatWordsAgain(
    exercisesCount: Int,
    openExercise: () -> Unit,
    initialHelpVisibility: Boolean = false
) {
    var showHelp by remember {
        mutableStateOf(initialHelpVisibility)
    }
    Column {
        Row {
            IconButton(
                onClick = {
                    showHelp = !showHelp
                }
            ) {
                Icon(
                    imageVector = SrIcons.Help,
                    contentDescription = null,
                )
            }
            Row(
                modifier = Modifier.horizontalScroll(
                    rememberScrollState()
                )
            ) {
                val buttonsCount = exercisesCount.coerceAtLeast(1)
                repeat(buttonsCount) {
                    Button(
                        onClick = openExercise,
                        modifier = Modifier.size(130.dp),
                        contentPadding = PaddingValues(14.dp),
                        shape = ShapeDefaults.Small,
                        enabled = exercisesCount > 0
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = SrIcons.RepeatAgain,
                                contentDescription = null
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Повторить еще раз",
                                style = MaterialTheme.typography.displayMedium
                            )
                            Row(
                                modifier = Modifier.fillMaxSize(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Text(
                                    text = (it + 1).toString(),
                                    style = MaterialTheme.typography.displaySmall
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        if (showHelp) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Слова, которые вы выбрали для повторения еще раз (этот выбор можно сделать в конце каждого упражнения). Каждая карточка содержит по семь слов.",
            )
        }
    }
}