package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
    Box (
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.displayMedium
        )
    }
}

@Preview
@Composable
fun ErrorBodyPreview() {
    ErrorBody(
        message = "Что-то пошло не так.",
        modifier = Modifier.fillMaxSize()
    )
}