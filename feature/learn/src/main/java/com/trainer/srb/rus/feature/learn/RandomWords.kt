package com.trainer.srb.rus.feature.learn

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
fun RandomWords(
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
            Button(
                onClick = openExercise,
                modifier = Modifier.size(130.dp),
                contentPadding = PaddingValues(14.dp),
                shape = ShapeDefaults.Small
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = SrIcons.Random,
                        contentDescription = null
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Случайные слова",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }
        if (showHelp) {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Случайные слова из словаря, кроме тех, которые вы уже выучили и тех, для которых вы выбрали опцию \"Не хочу учить это слово\".",
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun RandomWordsPreview() {
    MainTheme(dynamicColor = false) {
        RandomWords(
            openExercise = {}
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun RandomWordsWithHelpPreview() {
    MainTheme(dynamicColor = false) {
        RandomWords(
            openExercise = {},
            initialHelpVisibility = true
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RandomWordsNightPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            RandomWords(
                openExercise = {}
            )
        }
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RandomWordsWithHelpNightPreview() {
    MainTheme(dynamicColor = false) {
        Surface {
            RandomWords(
                openExercise = {},
                initialHelpVisibility = true
            )
        }
    }
}