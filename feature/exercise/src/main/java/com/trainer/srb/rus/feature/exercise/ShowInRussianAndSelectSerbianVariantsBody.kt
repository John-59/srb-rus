package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.russianAsString
import com.trainer.srb.rus.core.translation.serbianAsString

@Composable
fun ShowInRussianAndSelectSerbianVariantsBody(
    state: ExerciseStep.ShowInRussianAndSelectSerbianVariants,
    onNext: () -> Unit,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
    ) {
        Column(
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
                    enabled = !state.selectionIsDone,
                    modifier = Modifier.fillMaxWidth(),
                    colors = when (state.translationStates[it]) {
                        ExerciseStep.ShowInRussianAndSelectSerbianVariants.VariantState.RIGHT -> {
                            ButtonDefaults.outlinedButtonColors(
                                disabledContainerColor = MaterialTheme.colorScheme.inverseSurface,
                                disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        }
                        ExerciseStep.ShowInRussianAndSelectSerbianVariants.VariantState.WRONG -> {
                            ButtonDefaults.outlinedButtonColors(
                                disabledContainerColor = MaterialTheme.colorScheme.error,
                                disabledContentColor = MaterialTheme.colorScheme.onError
                            )
                        }
                        else -> {
                            ButtonDefaults.outlinedButtonColors()
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
                others = translationsExample.takeLast(3)
            )
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BeforeSelection_ShowInRussianAndSelectSerbianVariantsBodyNightPreview() {
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
                others = translationsExample.takeLast(3)
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
            others = translationsExample.takeLast(3)
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

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun RightSelection_ShowInRussianAndSelectSerbianVariantsBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStep.ShowInRussianAndSelectSerbianVariants(
            translation = translationsExample.first(),
            others = translationsExample.takeLast(3)
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

@Preview(apiLevel = 33)
@Composable
fun WrongSelection_ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStep.ShowInRussianAndSelectSerbianVariants(
            translation = translationsExample.first(),
            others = translationsExample.takeLast(3)
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
            state.select(translationsExample.last())
        }
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun WrongSelection_ShowInRussianAndSelectSerbianVariantsNightBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStep.ShowInRussianAndSelectSerbianVariants(
            translation = translationsExample.first(),
            others = translationsExample.takeLast(3)
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
            state.select(translationsExample.last())
        }
    }
}