package com.trainer.srb.rus.feature.docs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme

@Composable
fun LearningManual(
    modifier: Modifier = Modifier,
    viewModel: LearningManualViewModel = hiltViewModel(),
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MdDocument(viewModel.document)
        }
    }
}

@PreviewLightDark
@Composable
private fun LearningManualPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        LearningManual(
            viewModel = LearningManualViewModel(
                reader = object : IMdReader {
                    override fun read(fileName: String): String {
                        return """
                            # Запоминание слов.

                            Запоминание каждого слова состоит из следующих шагов:
                            1. Первое знакомство со словом.
                            2. Повторение слова на следующий день.
                            3. Повторение слова через два дня.
                            4. Через три дня.
                            5. Через неделю.
                            6. Через две недели.
                            7. Через месяц.

                            На каждом шаге вам будет предлагаться выбрать правильный перевод слова из предложенных вариантов, а так же написать слово на сербском.

                            Если на каком-то из шагов вы сделаете ошибку в слове, то прогресс изучения этого слова сбросится и шаги начнутся заново.

                            Если все шаги пройдены безошибочно - слово помечается в словаре как выученное.
                        """.trimIndent()
                    }
                }
            )
        )
    }
}