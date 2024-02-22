package com.trainer.srb.rus.feature.editword

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.MainTheme

@Composable
fun EditWordErrorBody(
    message: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
private fun EditWordErrorBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        EditWordErrorBody(
            message = "Не найден перевод с идентификатором = -1",
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EditWordErrorBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        EditWordErrorBody(
            message = "Не найден перевод с идентификатором = -1",
        )
    }
}