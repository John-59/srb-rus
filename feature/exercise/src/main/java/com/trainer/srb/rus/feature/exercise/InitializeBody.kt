package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.ui.LogoFadeInOut

@Composable
fun InitializeBody(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LogoFadeInOut()
    }
}

@Preview(apiLevel = 33)
@Composable
fun InitializeBodyPreview() {

}