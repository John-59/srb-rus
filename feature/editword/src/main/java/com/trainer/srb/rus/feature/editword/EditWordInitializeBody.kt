package com.trainer.srb.rus.feature.editword

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.ui.LogoFadeInOut

@Composable
fun EditWordInitializeBody(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        LogoFadeInOut()
    }
}

@Preview(apiLevel = 33)
@Composable
private fun EditWordInitializeBodyPreview() {
    EditWordInitializeBody(
        modifier = Modifier.fillMaxSize().padding(20.dp)
    )
}