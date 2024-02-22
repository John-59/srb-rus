package com.trainer.srb.rus.core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun LogoFadeInOut(
    modifier: Modifier = Modifier,
    initialAlpha: Float = 0F,
    targetAlpha: Float = 1F
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val alpha by infiniteTransition.animateFloat(
        initialValue = initialAlpha,
        targetValue = targetAlpha,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.alpha(alpha)
    ){
        Image(
            painter = painterResource(id = DesignRes.drawable.logo_primary),
            contentDescription = null,
        )
        Text(
            text = "Загружаем...",
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun LogoFadeInOutPreview() {
    LogoFadeInOut(
        initialAlpha = 1F,
        targetAlpha = 0F
    )
}