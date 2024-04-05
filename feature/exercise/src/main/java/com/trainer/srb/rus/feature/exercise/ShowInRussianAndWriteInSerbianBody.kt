package com.trainer.srb.rus.feature.exercise

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.exercise.ExerciseStep
import com.trainer.srb.rus.core.translation.LearningStatus
import com.trainer.srb.rus.core.translation.Translation
import com.trainer.srb.rus.core.translation.TranslationSourceType
import com.trainer.srb.rus.core.translation.Word
import com.trainer.srb.rus.core.translation.russianAsString
import com.trainer.srb.rus.core.translation.serbianAsString

@Composable
fun ShowInRussianAndWriteInSerbianBody(
    state: ExerciseStep.ShowInRussianAndWriteInSerbian,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    ShowInRussianAndWriteInSerbianBody(
        translation = state.translation,
        userInput = state.userInput,
        userInputValidity = state.userInputValidity,
        onUserInputChange = state::userInputChanged,
        onUserInputCheck = state::checkUserInput,
        onNext = onNext,
        modifier = modifier
    )
}

@Composable
private fun ShowInRussianAndWriteInSerbianBody(
    translation: Translation<Word.Serbian, Word.Russian>,
    userInput: String,
    userInputValidity: ExerciseStep.ShowInRussianAndWriteInSerbian.Validity,
    onUserInputChange: (String) -> Unit,
    onUserInputCheck: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember{ FocusRequester() }

    Surface(
        modifier = modifier
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(2f)
            ) {
                Text(
                    text = translation.russianAsString(),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(5.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    placeholder = {
                        Text(
                            text = "Напишите на сербском",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.outline,
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    value = userInput,
                    onValueChange = onUserInputChange,
                    colors = when (userInputValidity) {
                        ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.VALID -> {
                            OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                                unfocusedContainerColor = MaterialTheme.colorScheme.inverseSurface,
                                focusedTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                                unfocusedTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                            )
                        }
                        ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.INVALID -> {
                            OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = MaterialTheme.colorScheme.error,
                                unfocusedContainerColor = MaterialTheme.colorScheme.error,
                                focusedTextColor = MaterialTheme.colorScheme.onError,
                                unfocusedTextColor = MaterialTheme.colorScheme.onError,
                            )
                        }
                        else -> {
                            OutlinedTextFieldDefaults.colors()
                        }
                    },
                )
                Spacer(modifier = Modifier.padding(5.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(
                            if (userInputValidity == ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.INVALID) {
                                1.0f
                            } else {
                                0.0f
                            }
                        ),
                    textStyle = MaterialTheme.typography.titleMedium,
                    value = translation.serbianAsString(),
                    onValueChange = {},
                    colors =  OutlinedTextFieldDefaults.colors(
                        disabledContainerColor = MaterialTheme.colorScheme.inverseSurface,
                        disabledTextColor = MaterialTheme.colorScheme.inverseOnSurface,
                        disabledBorderColor = MaterialTheme.colorScheme.outline,
                    ),
                    enabled = false
                )
                Spacer(modifier = Modifier.padding(5.dp))

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = userInput.isNotBlank() && userInputValidity == ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED,
                    onClick = {
                        focusRequester.requestFocus()
                        onUserInputCheck()
                    }
                ) {
                    Text(
                        text = "Проверить",
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNext,
                enabled = userInputValidity != ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.UNDEFINED
            ) {
                Text(
                    text = "Далее",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(apiLevel = 33)
@Composable
private fun BeforeChecking_ShowInRussianAndWriteInSerbianBodyPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
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

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun BeforeChecking_ShowInRussianAndWriteInSerbianBodyNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
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

@Preview(apiLevel = 33)
@Composable
private fun ValidInput_ShowInRussianAndWriteInSerbianBodyPreview() {
    val translation = Translation(
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
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
            translation = translation,
            userInput = "kašika",
            userInputValidity = ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.VALID,
            onUserInputChange = {},
            onUserInputCheck = {},
            onNext = {},
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ValidInput_ShowInRussianAndWriteInSerbianBodyNightPreview() {
    val translation = Translation(
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
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
            translation = translation,
            userInput = "kašika",
            userInputValidity = ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.VALID,
            onUserInputChange = {},
            onUserInputCheck = {},
            onNext = {},
        )
    }
}

@Preview(apiLevel = 33)
@Composable
private fun WrongInput_ShowInRussianAndWriteInSerbianBodyPreview() {
    val translation = Translation(
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
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
            translation = translation,
            userInput = "wrong",
            userInputValidity = ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.INVALID,
            onUserInputChange = {},
            onUserInputCheck = {},
            onNext = {},
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WrongInput_ShowInRussianAndWriteInSerbianBodyNightPreview() {
    val translation = Translation(
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
    MainTheme(
        dynamicColor = false
    ) {
        ShowInRussianAndWriteInSerbianBody(
            translation = translation,
            userInput = "wrong",
            userInputValidity = ExerciseStep.ShowInRussianAndWriteInSerbian.Validity.INVALID,
            onUserInputChange = {},
            onUserInputCheck = {},
            onNext = {},
        )
    }
}