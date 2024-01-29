package com.trainer.srb.rus.feature.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.ui.CustomTextField
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun SearchAndAdd(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onAddClicked: (Word) -> Unit,
    onResetSearch: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
        Image(
            painter = if (value.text.isBlank()) {
                painterResource(id = DesignRes.drawable.search)
            } else {
                painterResource(id = DesignRes.drawable.addtodict_40_40)
            },
            contentDescription = null,
            modifier = Modifier.clickable {
                if (value.text.isNotBlank()) {
                    onAddClicked(Word.Serbian(latinValue = value.text, cyrillicValue = ""))
                }
            }
        )
        CustomTextField(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(2.0f),
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            placeholder = {
                Text(
                    text = "Поиск слов",
                    style = MainTheme.typography.displayMedium.copy(
                        baselineShift = BaselineShift(-0.2f)
                    ),
                    color = MainTheme.colors.Tips,
                )
            },
            textStyle = MainTheme.typography.displayMedium.copy(
                baselineShift = BaselineShift(-0.2f)
            ),
            value = value,
            onValueChange = onValueChange
        )
        Image(
            painter = if (value.text.isBlank()) {
                painterResource(DesignRes.drawable.cross_red)
            } else {
                painterResource(DesignRes.drawable.cross_red)
            },
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    onResetSearch()
                }
                .padding(5.dp)
        )
    }
}

@Preview(apiLevel = 33)
@Composable
fun SearchAndAddEmptyPreview() {
    SearchAndAdd(
        value = TextFieldValue(""),
        onValueChange = {},
        onAddClicked = {},
        onResetSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(apiLevel = 33)
@Composable
fun SearchAndAddPreview() {
    SearchAndAdd(
        value = TextFieldValue("lubenica"),
        onValueChange = {},
        onAddClicked = {},
        onResetSearch = {},
        modifier = Modifier.fillMaxWidth()
    )
}