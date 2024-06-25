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
fun DictionaryManual(
    modifier: Modifier = Modifier,
    viewModel: DictionaryManualViewModel = hiltViewModel(),
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
private fun DictionaryManualPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        DictionaryManual(
            viewModel = DictionaryManualViewModel(
                reader = object : IMdReader {
                    override fun read(fileName: String): String {
                        return """
                            # Словарь

                            Приложение содержит встроенный словарь (который периодически пополняется при выходе новых версий). Поэтому, вы можете пользоваться приложением даже при отсутствии интернета.

                            Чтобы найти слово в словаре, наберите его в строке поиска (можно вводить и на русском и на сербском латиницей или кириллицей):

                            ![Поиск слова](./img/find_words.png)

                            Если вы хотите добавить свое слово в словарь - нажмите на кнопку слева от введенного слова:

                            ![Кнопка добавления нового слова](./img/add_btn.png)

                            Чтобы отредактировать слово в словаре - нажмите на него однократно коротким нажатием.

                            Если слова нет в словаре или вы хотите посмотреть другие варианты перевода - нажмите кнопку (в зависимости от желаемого направления перевода) справа внизу экрана, чтобы найти слово в Google- и Yandex-переводчиках:

                            ![Кнопки поиска в онлайн-переводчиках](./img/internet_search_buttons.png)

                            Так же вы можете искать не только слова, но и целые фразы, например:

                            ![Поиск фраз](./img/find_phrase.png)

                            Вы можете удалить слово из словаря свайпом влево:

                            ![Удаление слова](./img/delete_from_dictionary.png)

                            Можете добавить слово в изучаемые (оно появится в упражнении "Новые слова") свайпом вправо:

                            ![Добавить в изучаемые](./img/add_to_learning.png)

                            Зеленая полоса под словом показывает прогресс изучения слова. Вы можете начать учить слово заново, если свайпнете его вправо:

                            ![Сбросить прогресс обучения](./img/reload_progress.png)
                        """.trimIndent()
                    }
                }
            )
        )
    }
}