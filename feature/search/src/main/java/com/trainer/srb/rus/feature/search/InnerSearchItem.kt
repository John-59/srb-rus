package com.trainer.srb.rus.feature.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.LearningStatus
import com.trainer.srb.rus.core.dictionary.Translation
import com.trainer.srb.rus.core.dictionary.TranslationSourceType
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun InnerSearchItem(
    translation: Translation<Word.Serbian, Word.Russian>,
    onEdit: (Translation<Word.Serbian, Word.Russian>) -> Unit,
    modifier: Modifier = Modifier
) {
    val serbian = listOf(
        translation.source.latinValue,
        translation.source.cyrillicValue
    ).filter {
        it.isNotBlank()
    }.joinToString(" / ")

    val russian = translation.translations.joinToString(", ") {
        it.value
    }

    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MainTheme.colors.Border)
            )
            .background(
                color = MainTheme.colors.White,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .weight(2.0f)
        ) {
            Text(
                text = serbian,
                style = MainTheme.typography.displayMedium
            )
            Text(
                text = russian,
                style = MainTheme.typography.displaySmall
            )
        }
        Image(
            painter = painterResource(id = DesignRes.drawable.wordindict),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 5.dp)
                .clickable {
                    onEdit(translation)
                }
        )
    }

}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewOne() {
    InnerSearchItem(
        translation = Translation(
            source = Word.Serbian(
                latinValue = "kiša",
                cyrillicValue = "киша"
            ),
            translations = listOf(
                Word.Russian(value = "дождь")
            ),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.Unknown()
        ),
        onEdit = {}
    )
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewTwo() {
    InnerSearchItem(
        translation = Translation(
            source = Word.Serbian(
                latinValue = "već",
                cyrillicValue = "већ"
            ),
            translations = listOf(
                Word.Russian(value = "уже"),
                Word.Russian(value = "а")
            ),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.Unknown()
        ),
        onEdit = {}
    )
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewThree() {
    InnerSearchItem(
        translation = Translation(
            source = Word.Serbian(
                latinValue = "šargarepa",
                cyrillicValue = ""
            ),
            translations = listOf(
                Word.Russian(value = "морковь"),
            ),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.Unknown()
        ),
        onEdit = {}
    )
}

@Preview(apiLevel = 33)
@Composable
private fun InnerSearchItemPreviewFour() {
    InnerSearchItem(
        translation = Translation(
            source = Word.Serbian(
                latinValue = "",
                cyrillicValue = "књига"
            ),
            translations = listOf(
                Word.Russian(value = "книга"),
            ),
            type = TranslationSourceType.USER,
            learningStatus = LearningStatus.Unknown()
        ),
        onEdit = {}
    )
}