package com.trainer.srb.rus.feature.exercise

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.trainer.srb.rus.core.exercise.ExerciseStep

@Composable
fun ShowInSerbianAndSelectRussianVariantsBody(
    state: ExerciseStepState.ShowInSerbianAndSelectRussianVariants,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@PreviewLightDark
@Composable
fun ShowInSerbianAndSelectRussianVariantsBodyPreview() {
    ShowInSerbianAndSelectRussianVariantsBody(
        state = ExerciseStepState.ShowInSerbianAndSelectRussianVariants(
            ExerciseStep.ShowInSerbianAndSelectRussianVariants()
        ),
        onNext = {}
    )
}