package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExerciseFinishedBody(
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Конец упражнения")
        Button(
            onClick = onNext
        ) {
            Text(text = "OK")
        }
    }

}

@Preview(apiLevel = 33)
@Composable
fun ExerciseFinishedBodyPreview() {
    ExerciseFinishedBody(
        onNext = {}
    )
}