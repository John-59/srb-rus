package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.SrIcons

@Composable
fun ExerciseTopBar(
    progress: Float,
    modifier: Modifier = Modifier,
    onSkip: () -> Unit,
    onExit: () -> Unit
) {
    val spacerWidth = Modifier.padding(5.dp)
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = SrIcons.Close,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable {
                onExit()
            }
        )
        Spacer(spacerWidth)
        LinearProgressIndicator(
            modifier = Modifier.weight(2f),
            color = MaterialTheme.colorScheme.inverseSurface,
            progress = progress,
        )
        Spacer(spacerWidth)
        Text(
            text = "Пропустить",
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable {
                onSkip()
            }
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun LearnTopBarPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ExerciseTopBar(
            progress = 0.7f,
            modifier = Modifier.fillMaxWidth(),
            onSkip = {},
            onExit = {}
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LearnTopBarNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        Surface {
            ExerciseTopBar(
                progress = 0.7f,
                modifier = Modifier.fillMaxWidth(),
                onSkip = {},
                onExit = {}
            )
        }
    }
}