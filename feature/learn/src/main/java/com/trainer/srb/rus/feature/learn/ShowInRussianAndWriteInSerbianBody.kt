package com.trainer.srb.rus.feature.learn

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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.dictionary.russianAsString
import com.trainer.srb.rus.core.dictionary.serbianAsString
import com.trainer.srb.rus.core.ui.CustomTextField

@Composable
fun ShowInRussianAndWriteInSerbianBody(
    state: LearnState.ShowInRussianAndWriteInSerbian,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WordAndWrite(
            state = state,
            modifier = Modifier
                .weight(2.0f)
                .fillMaxWidth()
        )
        Actions(
            isNextButtonEnabled = state.userInputValidity != LearnState.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED,
            onNext = onNext,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WordAndWrite(
    state: LearnState.ShowInRussianAndWriteInSerbian,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember{ FocusRequester() }
    Box (
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = state.translation.russianAsString(),
                style = MainTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
            Box (
                modifier = Modifier
                    .border(
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(2.dp, MainTheme.colors.Border)
                    )
                    .background(
                        color = when (state.userInputValidity) {
                            LearnState.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED -> {
                                MainTheme.colors.White
                            }

                            LearnState.ShowInRussianAndWriteInSerbian.Validity.VALID -> {
                                MainTheme.colors.Right
                            }

                            LearnState.ShowInRussianAndWriteInSerbian.Validity.INVALID -> {
                                MainTheme.colors.Wrong
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
                    modifier = Modifier.fillMaxWidth()
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.outlinedTextFieldColors(),
                    placeholder = {
                        Text(
                            text = "Напишите на сербском",
                            style = MainTheme.typography.titleMedium,
                            color = MainTheme.colors.Tips,
                        )
                    },
                    textStyle = MainTheme.typography.titleMedium,
                    value = state.userInput,
                    onValueChange = state::userInputChanged,
                    readOnly = state.userInputValidity != LearnState.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            if (state.userInputValidity == LearnState.ShowInRussianAndWriteInSerbian.Validity.INVALID) {
                Box(
                    modifier = Modifier
                        .border(
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(2.dp, MainTheme.colors.Border)
                        )
                        .background(
                            color = MainTheme.colors.Right,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxWidth()
                        .padding(10.dp),
                ) {
                    Text(
                        text = state.translation.serbianAsString(),
                        style = MainTheme.typography.titleMedium,
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }
            Button(
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MainTheme.colors.Buttons,
                    contentColor = MainTheme.colors.White,
                ),
                modifier = Modifier.fillMaxWidth(),
                enabled = state.userInput.isNotBlank() && state.userInputValidity == LearnState.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED,
                onClick = state::checkUserInput
            ) {
                Text(
                    text = "Проверить",
                    style = MainTheme.typography.displayMedium
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun Actions(
    isNextButtonEnabled: Boolean,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Button(onClick = {}) {
//            Text(
//                text = "Уже знаю это слово"
//            )
//        }
//        Button(onClick = {}) {
//            Text(
//                text = "Не хочу учить это слово"
//            )
//        }
//        Button(onClick = {}) {
//            Text(
//                text = "Учить заново"
//            )
//        }
        Button(
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MainTheme.colors.Buttons,
                contentColor = MainTheme.colors.White,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = isNextButtonEnabled,
            onClick = onNext
        ) {
            Text(
                text = "Далее",
                style = MainTheme.typography.displayMedium
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun ShowInRussianAndWriteInSerbianBodyPreview() {
    ShowInRussianAndWriteInSerbianBody(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        onNext = {},
        state = LearnState.ShowInRussianAndWriteInSerbian(
            translation = Translation(
                source = Word.Serbian(
                    latinValue = "kašika",
                    cyrillicValue = "кашика"
                ),
                translations = listOf(
                    Word.Russian(value = "ложка")
                )
            )
        ),
    )
}