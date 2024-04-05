package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun FinishBody(
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(2.0f)
            ) {
                Image(
                    painter = painterResource(id = DesignRes.drawable.finish),
                    contentDescription = null,
                )
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNext
            ) {
                Text(
                    text = "Упражнение закончено",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
fun FinishBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        FinishBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            onNext = {}
        )
    }
}