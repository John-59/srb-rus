package com.trainer.srb.rus.feature.exercise

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.russianAsString
import com.trainer.srb.rus.core.translation.serbianAsString
import com.trainer.srb.rus.core.ui.CustomTextField

@Composable
fun ShowInRussianAndWriteInSerbianBody(
    state: ExerciseStep.ShowInRussianAndWriteInSerbian,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember{ FocusRequester() }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(2f)
        ) {
            Text(
                text = state.translation.russianAsString(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Box(
                modifier = Modifier
                    .border(
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
                    )
                    .background(
                        color = when (state.userInputValidity) {
                            ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED -> {
                                MaterialTheme.colorScheme.background
                                //MainTheme.colors.White
                            }

                            ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.VALID -> {
                                MaterialTheme.colorScheme.error
                                //MainTheme.colors.Right
                            }

                            ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.INVALID -> {
                                MaterialTheme.colorScheme.error
                                //MainTheme.colors.Wrong
                            }
                        },
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.outlinedTextFieldColors(),
                    placeholder = {
                        Text(
                            text = "Напишите на сербском",
                            style = MaterialTheme.typography.titleMedium,
//                            color = MainTheme.colors.Tips,
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    value = state.userInput,
                    onValueChange = state::userInputChanged,
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))

            Box(
                modifier = Modifier
                    .alpha(
                        if (state.userInputValidity == ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.INVALID) {
                            1.0f
                        } else {
                            0.0f
                        }
                    )
                    .border(
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
                    )
//                    .background(
//                        color = MainTheme.colors.Right,
//                        shape = RoundedCornerShape(10.dp)
//                    )
                    .clip(
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth()
                    .padding(10.dp),
            ) {
                Text(
                    text = state.translation.serbianAsString(),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))

            Button(
                shape = RoundedCornerShape(10.dp),
//                colors = ButtonDefaults.buttonColors(
//                    backgroundColor = MainTheme.colors.Border,
//                    contentColor = MainTheme.colors.White,
//                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = state.userInput.isNotBlank() && state.userInputValidity == ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED,
                onClick = {
                    focusRequester.requestFocus()
                    state.checkUserInput()
                }
            ) {
                Text(
                    text = "Проверить",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }

        Button(
            shape = RoundedCornerShape(10.dp),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = MainTheme.colors.Buttons,
//                contentColor = MainTheme.colors.White,
//            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = onNext,
            enabled = state.userInputValidity != ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED
        ) {
            Text(
                text = "Далее",
                style = MaterialTheme.typography.displayMedium
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(apiLevel = 33)
@Composable
fun ShowInRussianAndWriteInSerbianBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            onNext = {},
            state = ExerciseStep.ShowInRussianAndWriteInSerbian(
                translation = Translation(
                    source = Word.Serbian(
                        latinValue = "kašika",
                        cyrillicValue = "кашика"
                    ),
                    translations = listOf(
                        Word.Russian(value = "ложка")
                    ),
                    type = TranslationSourceType.USER,
                    learningStatus = LearningStatus.Unknown()
                )
            )
        )
    }
}