package com.trainer.srb.rus.feature.learn

import android.content.res.Configuration
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons

@Composable
fun RepeatWords(
    repeatExercisesCount: Int,
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
                val buttonsCount = repeatExercisesCount.coerceAtLeast(1)
                repeat(buttonsCount) {
                    Button(
                        onClick = openExercise,
                        modifier = Modifier.size(130.dp),
                        contentPadding = PaddingValues(14.dp),
                        shape = ShapeDefaults.Small,
                        enabled = repeatExercisesCount > 0
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = SrIcons.Repeat,
                                contentDescription = null
                            )
                            Spacer(Modifier.height(10.dp))
                            Text(
                                text = "Повторение слов",
                                style = MaterialTheme.typography.displayMedium
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        if (showHelp) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Повторение слов, которые вы уже учили. Каждая карточка содержит по семь слов, которые вам надо повторить сегодня.",
            )
        }
    }
}

@Preview
@Composable
private fun RepeatWordsPreview() {
    MainTheme(dynamicColor = false) {
        RepeatWords(
            repeatExercisesCount = 2,
            openExercise = {}
        )
    }
}

@Preview
@Composable
private fun RepeatWordsDisabledPreview() {
    MainTheme(dynamicColor = false) {
        RepeatWords(
            repeatExercisesCount = 0,
            openExercise = {}
        )
    }
}

@Preview
@Composable
private fun RepeatWordsWithHelpPreview() {
    MainTheme(dynamicColor = false) {
        RepeatWords(
            repeatExercisesCount = 1,
            openExercise = {},
            initialHelpVisibility = true
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RepeatWordsNightPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            RepeatWords(
                repeatExercisesCount = 1,
                openExercise = {}
            )
        }
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RepeatWordsDisabledNightPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            RepeatWords(
                repeatExercisesCount = 0,
                openExercise = {}
            )
        }
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RepeatWordsWithHelpNightPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            RepeatWords(
                repeatExercisesCount = 3,
                openExercise = {},
                initialHelpVisibility = true
            )
        }
    }
}