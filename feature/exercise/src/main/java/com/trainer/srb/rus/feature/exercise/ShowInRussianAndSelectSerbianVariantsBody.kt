package com.trainer.srb.rus.feature.exercise

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.exercise.ExerciseStep
import com.trainer.srb.rus.core.mocks.translationsExample
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.serbianAsString

@Composable
fun ShowInRussianAndSelectSerbianVariantsBody(
    state: ExerciseStepState.ShowInRussianAndSelectSerbianVariants,
    onNext: () -> Unit,
    onAlreadyKnow: () -> Unit,
    onDontWantLearn: () -> Unit,
    modifier: Modifier = Modifier
) {
    InnerBody(
        russianWord = state.russian,
        variants = state.variants,
        selectionIsDone = state.selectionIsDone,
        select = state::select,
        checkSelection = {
            if (state.isValid(it)) {
                true
            } else if (state.isWrong(it)) {
                false
            } else {
                null
        }
        },
        onNext = onNext,
        onAlreadyKnow = onAlreadyKnow,
        onDontWantLearn = onDontWantLearn,
        modifier = modifier
    )
}

@Composable
private fun InnerBody(
    russianWord: String,
    variants: List<Translation<Word.Serbian, Word.Russian>>,
    selectionIsDone: Boolean,
    select: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    checkSelection: (Translation<Word.Serbian, Word.Russian>) -> Boolean?,
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
                russianWord = russianWord,
                variants = variants,
                selectionIsDone = selectionIsDone,
                select = select,
                checkSelection = checkSelection,
                modifier = Modifier
                    .weight(2.0f)
                    .fillMaxWidth()
            )
            WordActions(
                isNextEnabled = selectionIsDone,
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
    russianWord: String,
    variants: List<Translation<Word.Serbian, Word.Russian>>,
    selectionIsDone: Boolean,
    select: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    checkSelection: (Translation<Word.Serbian, Word.Russian>) -> Boolean?,
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
                text = russianWord,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            variants.forEach {
                OutlinedButton(
                    onClick = {
                        select(it)
                    },
                    enabled = !selectionIsDone,
                    modifier = Modifier.fillMaxWidth(),
                    colors = when (checkSelection(it)) {
                        true -> ButtonDefaults.outlinedButtonColors(
                            disabledContainerColor = MaterialTheme.colorScheme.inverseSurface,
                            disabledContentColor = MaterialTheme.colorScheme.inverseOnSurface
                        )
                        false -> ButtonDefaults.outlinedButtonColors(
                            disabledContainerColor = MaterialTheme.colorScheme.error,
                            disabledContentColor = MaterialTheme.colorScheme.onError
                        )
                        null -> ButtonDefaults.outlinedButtonColors()
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

@PreviewLightDark
@Composable
fun BeforeSelection_ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStepState.ShowInRussianAndSelectSerbianVariants(
            ExerciseStep.ShowInRussianAndSelectSerbianVariants(
                translation = translationsExample.first(),
                others = translationsExample.takeLast(3)
            )
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
    }
}

@PreviewLightDark
@Composable
fun RightSelection_ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStepState.ShowInRussianAndSelectSerbianVariants(
            ExerciseStep.ShowInRussianAndSelectSerbianVariants(
                translation = translationsExample.first(),
                others = translationsExample.takeLast(3)
            )
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

@PreviewLightDark
@Composable
fun WrongSelection_ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        val state = ExerciseStepState.ShowInRussianAndSelectSerbianVariants(
            ExerciseStep.ShowInRussianAndSelectSerbianVariants(
                translation = translationsExample.first(),
                others = translationsExample.takeLast(3)
            )
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