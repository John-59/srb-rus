package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun LearnTopBar(
    progress: Float,
    modifier: Modifier = Modifier,
    onSkip: () -> Unit,
    onExit: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = DesignRes.drawable.close),
            contentDescription = null,
            modifier = Modifier.clickable {
                onExit()
            }
        )
        Spacer(modifier = Modifier.padding(5.dp))
        LinearProgressIndicator(
            modifier = Modifier.weight(2f),
            progress = progress,
            trackColor = MainTheme.colors.Dark_40,
            color = MainTheme.colors.Border
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = "Пропустить",
            style = MainTheme.typography.displayMedium,
            color = MainTheme.colors.Border,
            modifier = Modifier.clickable {
                onSkip()
            }
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun LearnTopBarPreview() {
    LearnTopBar(
        progress = 0.7f,
        modifier = Modifier.fillMaxWidth(),
        onSkip = {},
        onExit = {}
    )
}