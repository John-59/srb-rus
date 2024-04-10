package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.exercise.ExerciseStep

@Composable
fun ErrorBody(
    state: ExerciseStepState.Error,
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
                text = state.message,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }

}

@PreviewLightDark
@Composable
fun ErrorBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ErrorBody(
            state = ExerciseStepState.Error(
                ExerciseStep.Error("Что-то пошло не так.")
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}