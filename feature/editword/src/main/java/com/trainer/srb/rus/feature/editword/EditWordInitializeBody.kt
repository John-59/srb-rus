package com.trainer.srb.rus.feature.editword

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.ui.LogoFadeInOut

@Composable
fun EditWordInitializeBody(
    modifier: Modifier = Modifier,
    initialAlpha: Float = 0f,
    targetAlpha: Float = 1f
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
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
private fun EditWordInitializeBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        EditWordInitializeBody(
            initialAlpha = 1f,
            targetAlpha = 0f
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditWordInitializeBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        EditWordInitializeBody(
            initialAlpha = 1f,
            targetAlpha = 0f
        )
    }
}