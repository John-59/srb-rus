package com.trainer.srb.rus.feature.about

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.mocks.DictionaryMock
import com.trainer.srb.rus.core.utils.IAppVersionProvider
import com.trainer.srb.rus.feature.docs.DictionaryManual
import com.trainer.srb.rus.feature.docs.LearningManual
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel()
) {
    val totalCount by viewModel.totalTranslationsCount.collectAsState()
    val userCount by viewModel.userTranslationsCount.collectAsState()
    val learningCount by viewModel.learningTranslationsCount.collectAsState()
    val learnedCount by viewModel.learnedTranslationsCount.collectAsState()
    val isLearningCurveManualVisible by viewModel.isLearningCurveManualVisible.collectAsState()
    val isDictionaryManualVisible by viewModel.isDictionaryManualVisible.collectAsState()

    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.logo_primary),
                contentDescription = null
            )
            Text(
                text = "Тренажер сербских слов",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Версия приложения: ${viewModel.appVersion}"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Всего слов в словаре: $totalCount"
            )
            Text(
                text = "Слов добавленных пользователем: $userCount"
            )
            Text(
                text = "Слов в процессе изучения: $learningCount"
            )
            Text(
                text = "Выучено слов: $learnedCount"
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Как устроено запоминание слов в приложении?",
                color = MaterialTheme.colorScheme.outline,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    viewModel.showLearningCurveManual()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Как работать со словарем?",
                color = MaterialTheme.colorScheme.outline,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    viewModel.showDictionaryManual()
                }
            )
        }
    }

    if (isLearningCurveManualVisible) {
        Dialog(
            onDismissRequest = viewModel::hideLearningCurveManual
        ) {
            LearningManual(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.9f)
            )
        }
    }

    if (isDictionaryManualVisible) {
        Dialog(
            onDismissRequest = viewModel::hideDictionaryManual
        ) {
            DictionaryManual(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.9f)
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun AboutScreenPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        AboutScreen(
            viewModel = AboutViewModel(
                appVersionProvider = object : IAppVersionProvider {
                    override val version: String
                        get() = "0.4.19"
                    override val predefinedDatabaseVersion: Int
                        get() = 19

                },
                dictionary = DictionaryMock()
            )
        )
    }
}

@Preview(apiLevel = 33, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AboutScreenNightPreview() {
    MainTheme(
        dynamicColor = false
    ) {
        AboutScreen(
            viewModel = AboutViewModel(
                appVersionProvider = object : IAppVersionProvider {
                    override val version: String
                        get() = "0.4.19"
                    override val predefinedDatabaseVersion: Int
                        get() = 19

                },
                dictionary = DictionaryMock()
            )
        )
    }
}