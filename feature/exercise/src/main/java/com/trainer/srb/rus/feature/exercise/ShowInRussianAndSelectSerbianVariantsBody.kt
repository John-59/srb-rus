package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.translation.russianAsString
import com.trainer.srb.rus.core.translation.serbianAsString
import com.trainer.srb.rus.core.mocks.translationsExample

@Composable
fun ShowInRussianAndSelectSerbianVariantsBody(
    state: ExerciseStep.ShowInRussianAndSelectSerbianVariants,
    onNext: () -> Unit,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WordAndVariants(
            state = state,
            modifier = Modifier
                .weight(2.0f)
                .fillMaxWidth()
        )
        WordActions(
            isNextEnabled = state.selectionIsDone,
            onNext = onNext,
            onAlreadyKnow = onAlreadyKnow,
            onDontWantLearn = onDontWantLearn,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WordAndVariants(
    state: ExerciseStep.ShowInRussianAndSelectSerbianVariants,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = state.translation.russianAsString(),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            state.variants.forEach {
                OutlinedButton(
                    onClick = {
                        state.select(it)
                    },
                    enabled = true,//!state.selectionIsDone,
                    modifier = Modifier.fillMaxWidth(),
//                    shape = RoundedCornerShape(10.dp),
//                    border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
                    colors = when (state.translationStates[it]) {
                        ExerciseStep.ShowInRussianAndSelectSerbianVariants.VariantState.RIGHT -> {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.inversePrimary,
//                                contentColor = MaterialTheme.colorScheme.inversePrimary,
//                                MainTheme.colors.Right,
//                                disabledBackgroundColor = MainTheme.colors.Right,
//                                contentColor = MainTheme.colors.Black,
//                                disabledContentColor = MainTheme.colors.Black
                            )
                        }
                        ExerciseStep.ShowInRussianAndSelectSerbianVariants.VariantState.WRONG -> {
                            ButtonDefaults.outlinedButtonColors(
                                containerColor = MaterialTheme.colorScheme.error,
//                                backgroundColor = MainTheme.colors.Wrong,
//                                disabledBackgroundColor = MainTheme.colors.Wrong,
//                                contentColor = MainTheme.colors.Black,
//                                disabledContentColor = MainTheme.colors.Black
                            )
                        }
                        else -> {
                            ButtonDefaults.outlinedButtonColors(
//                                backgroundColor = MainTheme.colors.White,
//                                disabledBackgroundColor = MainTheme.colors.White,
//                                contentColor = MainTheme.colors.Dark_80,
//                                disabledContentColor = MainTheme.colors.Dark_80
                            )
                        }
                    }
                ) {
                    Text(
                        text = it.serbianAsString(),
                        style = MaterialTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun BeforeSelection_ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndSelectSerbianVariantsBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            onNext = {},
            onAlreadyKnow = {},
            onDontWantLearn = {},
            state = ExerciseStep.ShowInRussianAndSelectSerbianVariants(
                translation = translationsExample.first(),
                others = translationsExample.take(3)
            )
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun RightSelection_ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStep.ShowInRussianAndSelectSerbianVariants(
            translation = translationsExample.first(),
            others = translationsExample.take(3)
        )
        ShowInRussianAndSelectSerbianVariantsBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            onNext = {},
            onAlreadyKnow = {},
            onDontWantLearn = {},
            state = state
        )

        LaunchedEffect(Unit) {
            state.select(translationsExample.first())
        }
    }
}