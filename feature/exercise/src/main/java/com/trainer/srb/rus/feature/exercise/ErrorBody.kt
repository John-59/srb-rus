package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.trainer.srb.rus.core.design.MainTheme

@Composable
fun ErrorBody(
    message: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }

}

@Preview
@Composable
fun ErrorBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ErrorBody(
            message = "Что-то пошло не так.",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ErrorBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ErrorBody(
            message = "Что-то пошло не так.",
            modifier = Modifier.fillMaxSize()
        )
    }
}