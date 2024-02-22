package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.ui.LogoFadeInOut

@Composable
fun InitializeBody(
    modifier: Modifier = Modifier,
    initialAlpha: Float = 0f,
    targetAlpha: Float = 1f
) {
    Surface(
        modifier = modifier
    ) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LogoFadeInOut(
                initialAlpha = initialAlpha,
                targetAlpha = targetAlpha
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun InitializeBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        InitializeBody(
            initialAlpha = 1f,
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun InitializeBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        InitializeBody(
            initialAlpha = 1f
        )
    }
}