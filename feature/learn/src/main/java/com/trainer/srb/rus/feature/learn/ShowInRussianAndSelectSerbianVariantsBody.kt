package com.trainer.srb.rus.feature.learn

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.dictionary.russianAsString
import com.trainer.srb.rus.core.dictionary.serbianAsString

@Composable
fun ShowInRussianAndSelectSerbianVariantsBody(
    state: LearnState.ShowInRussianAndSelectSerbianVariants,
    onNext: () -> Unit,
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
        Actions(
            isNextEnable = state.selectionIsDone,
            onNext = onNext,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun WordAndVariants(
    state: LearnState.ShowInRussianAndSelectSerbianVariants,
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
                style = MainTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            state.variants.forEach {
                Button(
                    onClick = {
                        state.select(it)
                    },
                    enabled = !state.selectionIsDone,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(2.dp, MainTheme.colors.Border),
                    colors = when (state.translationStates[it]) {
                        LearnState.ShowInRussianAndSelectSerbianVariants.VariantState.RIGHT -> {
                            ButtonDefaults.buttonColors(
                                backgroundColor = MainTheme.colors.Right,
                                disabledBackgroundColor = MainTheme.colors.Right,
                                contentColor = MainTheme.colors.White,
                                disabledContentColor = MainTheme.colors.White
                            )
                        }
                        LearnState.ShowInRussianAndSelectSerbianVariants.VariantState.WRONG -> {
                            ButtonDefaults.buttonColors(
                                backgroundColor = MainTheme.colors.Wrong,
                                disabledBackgroundColor = MainTheme.colors.Wrong,
                                contentColor = MainTheme.colors.White,
                                disabledContentColor = MainTheme.colors.White
                            )
                        }
                        else -> {
                            ButtonDefaults.buttonColors(
                                backgroundColor = MainTheme.colors.White,
                                disabledBackgroundColor = MainTheme.colors.White,
                                contentColor = MainTheme.colors.Dark_80,
                                disabledContentColor = MainTheme.colors.Dark_80
                            )
                        }
                    }
                ) {
                    Text(
                        text = it.serbianAsString(),
                        style = MainTheme.typography.titleSmall,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
    }
}

@Composable
private fun Actions(
    isNextEnable: Boolean,
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
            enabled = isNextEnable,
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
fun ShowInRussianAndSelectSerbianVariantsBodyPreview() {
    ShowInRussianAndSelectSerbianVariantsBody(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        onNext = {},
        state = LearnState.ShowInRussianAndSelectSerbianVariants(
            translation = Translation(
                source = Word.Serbian(
                    latinValue = "kašika",
                    cyrillicValue = "кашика"
                ),
                translations = listOf(
                    Word.Russian(value = "ложка")
                )
            ),
            others = listOf(
                Translation(
                    source = Word.Serbian(
                        latinValue = "viljuška",
                        cyrillicValue = "виљушка"
                    ),
                    translations = listOf(
                        Word.Russian(value = "вилка")
                    )
                ),
                Translation(
                    source = Word.Serbian(
                        latinValue = "tanjir",
                        cyrillicValue = "тањир"
                    ),
                    translations = listOf(
                        Word.Russian(value = "тарелка")
                    )
                ),
                Translation(
                    source = Word.Serbian(
                        latinValue = "sto",
                        cyrillicValue = "сто"
                    ),
                    translations = listOf(
                        Word.Russian(value = "стол")
                    )
                )
            )
        ),
    )
}