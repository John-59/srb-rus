package com.trainer.srb.rus.feature.exercise

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowInSerbianAndSelectRussianVariantsBody(
    state: ExerciseStep.ShowInSerbianAndSelectRussianVariants,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(apiLevel = 33)
@Composable
fun ShowInSerbianAndSelectRussianVariantsBodyPreview() {
    ShowInSerbianAndSelectRussianVariantsBody(
        state = ExerciseStep.ShowInSerbianAndSelectRussianVariants(),
        onNext = {}
    )
}