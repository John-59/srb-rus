package com.trainer.srb.rus.feature.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trainer.srb.rus.core.design.MainTheme
import com.trainer.srb.rus.core.dictionary.Word
import com.trainer.srb.rus.core.ui.CustomTextField
import com.trainer.srb.rus.core.design.R as DesignRes

@Composable
fun SearchAndAdd(
    value: String,
    onValueChange: (String) -> Unit,
    onAddClicked: (Word) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .border(
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, MainTheme.colors.Border)
            ),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = DesignRes.drawable.search),
                contentDescription = null,
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
                painter = if (value.isBlank()) {
                    painterResource(DesignRes.drawable.addtodictgray)
                } else {
                    painterResource(DesignRes.drawable.addtodict)
                },
                contentDescription = null,
                modifier = Modifier.clickable {
                    onAddClicked(Word.Serbian(latinValue = value, cyrillicValue = ""))
                }
            )
        }
    }
}

@Preview(apiLevel = 33)
@Composable
fun SearchAndAddPreview() {
    SearchAndAdd(
        value = "",
        onValueChange = {},
        onAddClicked = {},
        modifier = Modifier.fillMaxWidth()
    )
}