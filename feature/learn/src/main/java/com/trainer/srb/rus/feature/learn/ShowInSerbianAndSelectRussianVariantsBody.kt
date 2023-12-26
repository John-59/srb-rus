package com.trainer.srb.rus.feature.learn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ShowInSerbianAndSelectRussianVariantsBody(
    state: LearnState.ShowInSerbianAndSelectRussianVariants,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {

}

@Preview(apiLevel = 33)
@Composable
fun ShowInSerbianAndSelectRussianVariantsBodyPreview() {
    ShowInSerbianAndSelectRussianVariantsBody(
        state = LearnState.ShowInSerbianAndSelectRussianVariants(),
        onNext = {}
    )
}